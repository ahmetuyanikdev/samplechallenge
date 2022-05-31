package com.fleetmanagement.model;

public class Shipment {

    private Vehicle vehicle;

    private String status;

    private DeliveryPoint deliveryPoint;

    public void load() {

    }

    public void unload() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }
}
