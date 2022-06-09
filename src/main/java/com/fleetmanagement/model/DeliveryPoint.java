package com.fleetmanagement.model;

import com.fleetmanagement.data.DeliveryPointDataList;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "deliverypoints")
public class DeliveryPoint {

    @Id
    private int id;

    private String type;

    @OneToMany(mappedBy = "deliveryPoint",fetch = FetchType.EAGER)
    private Set<Route> routes;

    public DeliveryPoint() {

    }

    public DeliveryPoint(DeliveryPointDataList.DeliveryPointData deliveryPointData) {
        this.id = deliveryPointData.getId();
        this.type = deliveryPointData.getType();
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
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
