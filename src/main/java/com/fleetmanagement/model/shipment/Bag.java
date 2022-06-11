package com.fleetmanagement.model.shipment;

import com.fleetmanagement.converter.UnloadCalculation;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Bag extends Shipment implements UnloadCalculation {

    @OneToMany(mappedBy = "bag")
    private Set<Package> packages;

    public void addPackage(Package pack) {
        packages.add(pack);
    }

    public void removeItem(Package pack) {
        packages.remove(pack);
    }

    public static Bag getInstance() {
        return new Bag();
    }

    public Set<Package> getPackages() {
        return packages;
    }

    @Override
    public ShipmentUnloadCalculation returnCalculationMethod() {
        return new BagUnloadingUnloadCalculation(this);
    }

    @Override
    public PostUpdateShipmentUnloadCalculation getPostUpdateCalculationMethod() {
        return returnPostUpdateCalculationMethod();
    }

    @Override
    public PostUpdateShipmentUnloadCalculation returnPostUpdateCalculationMethod() {
        return new BagUnloadingUnloadCalculation(this);
    }

    @Override
    public ShipmentUnloadCalculation getUnloadCalculationMethod() {
        return returnCalculationMethod();
    }
}
