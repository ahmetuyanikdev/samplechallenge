package com.fleetmanagement.data;

import java.util.List;

public class RouteData {

    public RouteData() {

    }

    private int deliveryPoint;

    private List<ItemBarcode> deliveries;

    public static class ItemBarcode {

        public String barcode;

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
    }

    public int getDeliveryPoint() {
        return deliveryPoint;
    }

    public List<ItemBarcode> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<ItemBarcode> deliveries) {
        this.deliveries = deliveries;
    }

    public void setDeliveryPoint(int deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

}
