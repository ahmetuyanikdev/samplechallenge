package com.fleetmanagement.model.item;

import com.fleetmanagement.model.DeliveryPoint;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }
}
