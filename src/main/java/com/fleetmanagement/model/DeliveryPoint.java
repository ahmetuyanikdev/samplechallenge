package com.fleetmanagement.model;

import com.fleetmanagement.data.DeliveryPointDataList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
Branch	1
Distribution Center	2
Transfer Center	3
*/
@Entity
@Table(name = "deliverypoints")
public class DeliveryPoint {

    @Id
    private int id;

    private String type;

    public DeliveryPoint() {

    }

    public DeliveryPoint(DeliveryPointDataList.DeliveryPointData deliveryPointData) {
        this.id = deliveryPointData.getId();
        this.type = deliveryPointData.getType();
    }

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
