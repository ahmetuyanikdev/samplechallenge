package com.fleetmanagement.data.shipment;

import java.util.List;

public class ShipmentAssignmentDataList {

    private List<ShipmentAssignment> shipmentAssignments;

    public ShipmentAssignmentDataList() {
    }

    public static class ShipmentAssignment {

        private String barcode;
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
