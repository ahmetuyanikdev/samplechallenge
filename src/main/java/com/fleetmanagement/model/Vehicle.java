package com.fleetmanagement.model;

import com.fleetmanagement.model.shipment.Shipment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table (name = "vehicles")
public class Vehicle {

    public Vehicle(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Vehicle() {

    }

    @Id
    private String plateNumber;

    @OneToMany(mappedBy = "vehicle")
    private Set<Shipment> shipments;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setShipments(Set<Shipment> shipments) {
        this.shipments = shipments;
    }
}
