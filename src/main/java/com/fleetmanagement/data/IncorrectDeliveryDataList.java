package com.fleetmanagement.data;

import java.util.List;

public class IncorrectDeliveryDataList {

    private int deliveryPoint;
    private List<ShipmentData> deliveries;

    public static class ShipmentData {


        public ShipmentData(String barcode) {
            this.barcode = barcode;
        }

        private String barcode;

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

    public void setDeliveryPoint(int deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public List<ShipmentData> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<ShipmentData> deliveries) {
        this.deliveries = deliveries;
    }
}
