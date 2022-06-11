package com.fleetmanagement.data.shipment;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ShipmentAssignmentDataList {

    private List<@Valid ShipmentAssignment> shipmentAssignments;

    public ShipmentAssignmentDataList() {
    }

    public static class ShipmentAssignment {

        @NotNull(message = "{shipment.barcode.missing}")
        private String barcode;

        @NotNull(message = "{shipment.barcode.missing}")
        private String bagBarcode;

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getBagBarcode() {
            return bagBarcode;
        }

        public void setBagBarcode(String bagBarcode) {
            this.bagBarcode = bagBarcode;
        }
    }

    public List<ShipmentAssignment> getShipmentAssignments() {
        return shipmentAssignments;
    }

    public void setShipmentAssignments(List<ShipmentAssignment> shipmentAssignments) {
        this.shipmentAssignments = shipmentAssignments;
    }
}
