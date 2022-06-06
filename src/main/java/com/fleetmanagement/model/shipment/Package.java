package com.fleetmanagement.model.shipment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Package extends Shipment {

    private int volumetricWeight;

    @ManyToOne
    @JoinColumn(name = "bag_barcode", referencedColumnName = "barcode")
    private Bag bag;

    public int getVolumetricWeight() {
        return volumetricWeight;
    }

    public void setVolumetricWeight(int volumetricWeight) {
        this.volumetricWeight = volumetricWeight;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public static Package getInstance() {
        return new Package();
    }
}
