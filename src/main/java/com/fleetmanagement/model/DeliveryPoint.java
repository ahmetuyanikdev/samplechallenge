package com.fleetmanagement.model;

import com.fleetmanagement.data.DeliveryPointDataList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "deliverypoints")
public class DeliveryPoint {

    @Id
    private int id;

    private String type;

    @OneToOne(mappedBy = "deliveryPoint")
    private Route route;

    public DeliveryPoint() {

    }

    public DeliveryPoint(DeliveryPointDataList.DeliveryPointData deliveryPointData) {
        this.id = deliveryPointData.getId();
        this.type = deliveryPointData.getType();
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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
