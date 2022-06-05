package com.fleetmanagement.model;

import com.fleetmanagement.model.item.Shipment;

public class UnmatchedDelivery {

    private DeliveryPoint deliveryPoint;
    private Shipment shipment;

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
}
