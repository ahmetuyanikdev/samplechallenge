package com.fleetmanagement.model.shipment;

import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Vehicle;

import javax.persistence.*;

@Entity
@Table(name = "shipments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Shipment {

    @Id
    private String barcode;

    @OneToOne
    @JoinColumn(name = "deliveryPoint_id",referencedColumnName = "id")
    private DeliveryPoint deliveryPoint;

    @ManyToOne
    @JoinColumn(name = "vehicle_plateNumber",referencedColumnName = "plateNumber",nullable = true)
    private Vehicle vehicle;

    private String status;


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }
}
