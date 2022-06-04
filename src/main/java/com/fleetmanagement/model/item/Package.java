package com.fleetmanagement.model.item;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Package extends Item {

    private int volumetricWeight;

    @ManyToOne
    @JoinColumn(name = "id",nullable = true)
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
}
