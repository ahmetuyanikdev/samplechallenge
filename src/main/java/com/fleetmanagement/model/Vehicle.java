package com.fleetmanagement.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
