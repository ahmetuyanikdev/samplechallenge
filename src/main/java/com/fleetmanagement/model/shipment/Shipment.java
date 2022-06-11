package com.fleetmanagement.model.shipment;

import com.fleetmanagement.converter.UnloadCalculation;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "shipments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Shipment {

    @Id
    private String barcode;

    @OneToOne
    @JoinColumn(name = "deliveryPoint_id", referencedColumnName = "id", nullable = false)
    private DeliveryPoint deliveryPoint;

    @ManyToMany(mappedBy = "deliveries",fetch = FetchType.EAGER)
    private Set<Route> routes;

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

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public UnloadCalculation.ShipmentUnloadCalculation getUnloadCalculationMethod() {
        throw new UnsupportedOperationException();
    }

    public UnloadCalculation.PostUpdateShipmentUnloadCalculation getPostUpdateCalculationMethod() {
        throw new UnsupportedOperationException();
    }
}
