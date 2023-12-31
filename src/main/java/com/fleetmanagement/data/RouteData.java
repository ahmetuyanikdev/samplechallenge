package com.fleetmanagement.data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RouteData {

    public RouteData() {

    }

    @NotNull(message = "{route.delivery-point.missing}")
    private int deliveryPoint;

    private List<@Valid ShipmentInformation> deliveries;

    public static class ShipmentInformation {

        @NotNull(message = "{route.barcode.missing}")
        private String barcode;

        private int state;

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }

    public int getDeliveryPoint() {
        return deliveryPoint;
    }

    public List<ShipmentInformation> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<ShipmentInformation> deliveries) {
        this.deliveries = deliveries;
    }

    public void setDeliveryPoint(int deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

}
