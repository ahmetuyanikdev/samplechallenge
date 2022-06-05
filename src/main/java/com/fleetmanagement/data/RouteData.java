package com.fleetmanagement.data;

import java.util.List;

public class RouteData {

    public RouteData() {

    }

    private int deliveryPoint;

    private List<ShipmentBarcode> deliveries;

    public static class ShipmentBarcode {

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

    public List<ShipmentBarcode> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<ShipmentBarcode> deliveries) {
        this.deliveries = deliveries;
    }

    public void setDeliveryPoint(int deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

}
