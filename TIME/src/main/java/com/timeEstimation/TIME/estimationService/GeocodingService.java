package com.timeEstimation.TIME.estimationService;

import com.timeEstimation.TIME.dto.Coordinates;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class GeocodingService {
    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);

    @Value("${geocoding.api.url}")
    private String geocodingApiUrl;

    @Value("${geocoding.api.user-agent}")
    private String userAgent;

    public Coordinates getCoordinates(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1000);

            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String url = geocodingApiUrl + "?q=" + encodedAddress + "&format=json&limit=1";
            logger.debug("Geocoding API URL: {}", url);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", userAgent);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Geocoding API returned status: " + responseEntity.getStatusCode());
            }

            String jsonResponse = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);

            if (root.isArray() && root.size() > 0) {
                JsonNode firstResult = root.get(0);
                if (!firstResult.has("lat") || !firstResult.has("lon")) {
                    throw new RuntimeException("Invalid response format: Missing 'lat' or 'lon' fields");
                }
                double lat = firstResult.path("lat").asDouble();
                double lon = firstResult.path("lon").asDouble();
                return new Coordinates(lat, lon);
            } else {
                throw new RuntimeException("No results found for address: " + address);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while waiting for rate limit delay", e);
        } catch (Exception e) {
            logger.error("Error retrieving coordinates for address: {}", address, e);
            throw new RuntimeException("Error retrieving coordinates for address: " + address, e);
        }
    }
}