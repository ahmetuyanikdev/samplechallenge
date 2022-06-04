package com.fleetmanagement.data.item;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ItemDataList {

    public ItemDataList() {
    }

    private List<ItemData> items;

    public static class ItemData{

        private String barcode;
        private int deliveryPoint;

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
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }
}
