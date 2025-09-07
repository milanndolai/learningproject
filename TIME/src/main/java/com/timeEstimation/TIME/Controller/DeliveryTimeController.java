package com.timeEstimation.TIME.Controller;

import com.timeEstimation.TIME.dto.DeliveryTimeRequest;
import com.timeEstimation.TIME.dto.DeliveryTimeResponse;
import com.timeEstimation.TIME.dto.Coordinates;
import com.timeEstimation.TIME.estimationService.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Adjust as needed for your frontend
public class DeliveryTimeController {

    @Autowired
    private GeocodingService geocodingService;

    
    private static final int SHIPPING_PREPARATION_HOURS = 0;
    
    private static final double MEDIUM_SPEED_KMH = 40.0;

    @PostMapping("/deliveryTime")
    public ResponseEntity<DeliveryTimeResponse> estimateDeliveryTime(@RequestBody DeliveryTimeRequest request) {
        //adresses
        Coordinates pickupCoordinates = geocodingService.getCoordinates(request.getPickupAddress());
        Coordinates deliveryCoordinates = geocodingService.getCoordinates(request.getDeliveryAddress());

        // Calculate distance using the Haversine formula (in kilometers)
        double distance = calculateDistanceBetween(pickupCoordinates, deliveryCoordinates);

        // Get dynamic average speed based on current time and traffic conditions
        double dynamicSpeed = getAverageSpeedBasedOnTraffic();
        
        // Calculate transit time based on distance and dynamic average speed
        double estimatedTransitTimeHours = distance / dynamicSpeed;
        
        // If the estimated transit time exceeds 1 day, recalculate using medium speed (40 km/hr)
        if (estimatedTransitTimeHours > 24) {
            estimatedTransitTimeHours = distance / MEDIUM_SPEED_KMH;
        }
        
        double estimatedTransitTimeMinutes = estimatedTransitTimeHours * 60;

        // Convert shipping preparation time (6 hrs) to minutes
        long shippingPreparationMinutes = SHIPPING_PREPARATION_HOURS * 60;

        // Total estimated time = transit time + shipping preparation time
        long transitTimeMinutesRounded = Math.round(estimatedTransitTimeMinutes);
        long totalEstimatedTimeMinutes = transitTimeMinutesRounded + shippingPreparationMinutes;

        // Calculate the estimated delivery timestamp by adding the total time to current time
        LocalDateTime estimatedDeliveryDateTime = LocalDateTime.now().plusMinutes(totalEstimatedTimeMinutes);

        // Adjust the estimated delivery time if it falls between 11pm and 7am
        estimatedDeliveryDateTime = adjustForNoLateNightDelivery(estimatedDeliveryDateTime);

        // Format the estimated delivery time as an ISO 8601 string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM, HH:mm", Locale.ENGLISH);
        String estimatedDeliveryTimeStr = estimatedDeliveryDateTime.format(formatter);

        // Create and return the response
        DeliveryTimeResponse response = new DeliveryTimeResponse(totalEstimatedTimeMinutes, estimatedDeliveryTimeStr);
        return ResponseEntity.ok(response);
    }

    
    private double calculateDistanceBetween(Coordinates c1, Coordinates c2) {
        final int R = 6371; // Earth's radius in kilometers

        double latDistance = Math.toRadians(c2.getLatitude() - c1.getLatitude());
        double lonDistance = Math.toRadians(c2.getLongitude() - c1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(c1.getLatitude())) * Math.cos(Math.toRadians(c2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Determines the average speed based on current time (simulated traffic conditions)
    private double getAverageSpeedBasedOnTraffic() {
        LocalTime now = LocalTime.now();

        // Low traffic: 10 PM to 7 AM (faster speed)
        if (now.compareTo(LocalTime.of(22, 0)) >= 0 || now.compareTo(LocalTime.of(7, 0)) < 0) {
            return 65.0;
        }
        // High traffic: 6 PM to 9 PM (slower speed)
        else if (now.compareTo(LocalTime.of(18, 0)) >= 0 && now.compareTo(LocalTime.of(21, 0)) < 0) {
            return 25.0;
        }
        // Medium traffic: default during other times
        else {
            return 40.0;
        }
    }

    // Adjusts the estimated delivery time: if it falls between 11pm and 7am, sets it to 8am.
    private LocalDateTime adjustForNoLateNightDelivery(LocalDateTime estimatedDeliveryDateTime) {
        LocalTime deliveryTime = estimatedDeliveryDateTime.toLocalTime();
        // Check if the time falls between 11:00 PM and 7:00 AM.
        if (deliveryTime.isBefore(LocalTime.of(7, 0)) || !deliveryTime.isBefore(LocalTime.of(23, 0))) {
            // If the estimated time is 23:00 or later, adjust to next day 8:00 AM.
            if (!deliveryTime.isBefore(LocalTime.of(23, 0))) {
                return estimatedDeliveryDateTime.toLocalDate().plusDays(1).atTime(8, 0);
            } else {
                // If the time is before 7:00 AM, adjust to 8:00 AM of the same day.
                // However, if that time is already past compared to now, schedule for the next day.
                LocalDateTime adjusted = estimatedDeliveryDateTime.toLocalDate().atTime(8, 0);
                if (adjusted.isBefore(LocalDateTime.now())) {
                    adjusted = adjusted.plusDays(1);
                }
                return adjusted;
            }
        }
        return estimatedDeliveryDateTime;
    }
}
