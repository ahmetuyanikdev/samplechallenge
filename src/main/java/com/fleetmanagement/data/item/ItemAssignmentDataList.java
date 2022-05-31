package com.fleetmanagement.data.item;

import java.util.List;

public class ItemAssignmentDataList {

    private List<ItemAssignment> itemAssignments;

    public ItemAssignmentDataList() {
    }

    public static class ItemAssignment{

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

    public List<ItemAssignment> getItemAssignments() {
        return itemAssignments;
    }

    public void setItemAssignments(List<ItemAssignment> itemAssignments) {
        this.itemAssignments = itemAssignments;
    }
}
