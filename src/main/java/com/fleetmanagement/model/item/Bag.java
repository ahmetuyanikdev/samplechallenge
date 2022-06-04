package com.fleetmanagement.model.item;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Bag extends Item {

    @OneToMany(mappedBy = "bag")
    private Set<Package> packages;

    public void addItem(Package pack){
        packages.add(pack);
    }
    public void removeItem(Package pack){
        packages.remove(pack);
    }
}
