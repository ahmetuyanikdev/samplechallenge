package com.fleetmanagement.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "incorrectdeliveries")
public class IncorrectDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @NotNull
    private int deliveryPointId;

    @NotNull
    private String barcode;

    public int getDeliveryPointId() {
        return deliveryPointId;
    }

    public void setDeliveryPointId(int deliveryPointId) {
        this.deliveryPointId = deliveryPointId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static IncorrectDelivery getInstance(int deliveryPointId, String barcode) {
        IncorrectDelivery incorrectDelivery = new IncorrectDelivery();
        incorrectDelivery.setBarcode(barcode);
        incorrectDelivery.setDeliveryPointId(deliveryPointId);
        return incorrectDelivery;
    }
}
