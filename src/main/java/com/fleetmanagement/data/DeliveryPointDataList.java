package com.fleetmanagement.data;

import com.fleetmanagement.model.DeliveryPoint;

import java.io.Serializable;
import java.util.List;

public class DeliveryPointDataList implements Serializable {

    private List<DeliveryPointData> deliveryPoints;

    public DeliveryPointDataList() {

    }

    public static class DeliveryPointData{

        public DeliveryPointData() {

        }

        public DeliveryPointData(DeliveryPoint deliveryPoint){
            id=deliveryPoint.getId();
            type=deliveryPoint.getType();
        }

        private int id;

        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public List<DeliveryPointData> getDeliveryPoints() {
        return deliveryPoints;
    }

    public void setDeliveryPoints(List<DeliveryPointData> deliveryPoints) {
        this.deliveryPoints = deliveryPoints;
    }
}
