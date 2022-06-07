package com.fleetmanagement.model.shipment;

import com.fleetmanagement.converter.UnloadCalculation;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Package extends Shipment implements UnloadCalculation {

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

    @Override
    public ShipmentUnloadCalculation returnCalculationMethod() {
        return Objects.isNull(this.getBag()) ? new PackageUnloadingUnloadCalculation(this) : new PackageAssignedBagUnloadingUnloadCalculation(this);
    }

    @Override
    public ShipmentUnloadCalculation getUnloadCalculationMethod() {
        return returnCalculationMethod();
    }
}
