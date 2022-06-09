package com.fleetmanagement.data;

import com.fleetmanagement.model.DeliveryPoint;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class DeliveryPointDataList {

    private List<@Valid DeliveryPointData> deliveryPoints;

    public DeliveryPointDataList() {

    }

    public static class DeliveryPointData{

        public DeliveryPointData() {

        }

        public DeliveryPointData(DeliveryPoint deliveryPoint){
            id=deliveryPoint.getId();
            type=deliveryPoint.getType();
        }

        @NotNull(message = "delivery point id missing")
        private int id;

        @NotNull(message = "delivery point type missing")
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
