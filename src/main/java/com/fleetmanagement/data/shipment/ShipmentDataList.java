package com.fleetmanagement.data.shipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class ShipmentDataList {

    public ShipmentDataList() {
    }

    private List<@Valid ShipmentData> shipments;

    public static class ShipmentData {

        @NotNull(message = "Barcode is missing")
        private String barcode;
        @NotNull(message = "Delivery point is missing")
        private int deliveryPoint;

        private int status;

        private int volumetricWeight;

        @JsonIgnore
        public boolean isPackage() {
            return Objects.nonNull(volumetricWeight) && volumetricWeight > 0;
        }

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
