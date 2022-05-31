package com.fleetmanagement.model.item;

import com.fleetmanagement.model.DeliveryPoint;

public class Item {

    private String barcode;

    private DeliveryPoint deliveryPoint;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }
}
