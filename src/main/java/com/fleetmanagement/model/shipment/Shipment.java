package com.fleetmanagement.model.shipment;

import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.Vehicle;

import javax.persistence.*;

@Entity
@Table(name = "shipments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Shipment {

    @Id
    private String barcode;

    @OneToOne
    @JoinColumn(name = "deliveryPoint_id",referencedColumnName = "id",nullable = false)
    private DeliveryPoint deliveryPoint;

    @ManyToOne
    @JoinColumn(name = "route_id",referencedColumnName = "id")
    private Route route;

    private int status;


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }


    public int getStatus() {
        return status;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }
}
