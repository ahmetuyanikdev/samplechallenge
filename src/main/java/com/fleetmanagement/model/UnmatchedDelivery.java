package com.fleetmanagement.model;

import com.fleetmanagement.model.item.Item;

public class UnmatchedDelivery {

    private DeliveryPoint deliveryPoint;
    private Item item;

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
