package com.fleetmanagement.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "vehicles")
public class Vehicle {

    public Vehicle(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Vehicle() {
    }

    @Id
    private String plateNumber;

    @OneToMany(mappedBy = "vehicle")
    private Set<Route> routes;

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
