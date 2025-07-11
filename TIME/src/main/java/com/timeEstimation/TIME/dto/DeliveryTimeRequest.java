package com.timeEstimation.TIME.dto;


	public class DeliveryTimeRequest {
	    private String pickupAddress;
	    private String deliveryAddress;

	    public String getPickupAddress() {
	        return pickupAddress;
	    }
	    public void setPickupAddress(String pickupAddress) {
	        this.pickupAddress = pickupAddress;
	    }
	    public String getDeliveryAddress() {
	        return deliveryAddress;
	    }
	    public void setDeliveryAddress(String deliveryAddress) {
	        this.deliveryAddress = deliveryAddress;
	    }
	}


