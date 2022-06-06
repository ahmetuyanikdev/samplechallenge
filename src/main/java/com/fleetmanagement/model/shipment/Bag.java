package com.fleetmanagement.model.shipment;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Bag extends Shipment {

    @OneToMany(mappedBy = "bag")
    private Set<Package> packages;

    public void addPackage(Package pack){
        packages.add(pack);
    }
    public void removeItem(Package pack){
        packages.remove(pack);
    }

    public static Bag getInstance(){
        return new  Bag();
    }
}
