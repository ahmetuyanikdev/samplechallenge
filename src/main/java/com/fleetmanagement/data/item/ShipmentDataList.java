package com.fleetmanagement.data.item;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ShipmentDataList {

    public ShipmentDataList() {
    }

    private List<ShipmentData> shipments;

    public static class ShipmentData {

        private String barcode;
        private int deliveryPoint;
        private int status;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private int volumetricWeight;

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public int getDeliveryPoint() {
            return deliveryPoint;
        }

        public void setDeliveryPoint(int deliveryPoint) {
            this.deliveryPoint = deliveryPoint;
        }

        public int getVolumetricWeight() {
            return volumetricWeight;
        }

        public void setVolumetricWeight(int volumetricWeight) {
            this.volumetricWeight = volumetricWeight;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public List<ShipmentData> getShipments() {
        return shipments;
    }

    public void setShipments(List<ShipmentData> shipments) {
        this.shipments = shipments;
    }
}
