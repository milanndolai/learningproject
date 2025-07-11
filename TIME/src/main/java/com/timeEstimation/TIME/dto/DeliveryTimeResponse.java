package com.timeEstimation.TIME.dto;

public class DeliveryTimeResponse {
      private double estimatedTimeInMinutes;
      private String estimatedDeliveryTime;
	public double getEstimatedTimeInMinutes() {
		return estimatedTimeInMinutes;
	}
	public void setEstimatedTimeInMinutes(double estimatedTimeInMinutes) {
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
	}
	public String getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}
	public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}
	public DeliveryTimeResponse(double estimatedTimeInMinutes, String estimatedDeliveryTime) {
		super();
		this.estimatedTimeInMinutes = estimatedTimeInMinutes;
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}
      
}
