package com.fleetmanagement.data.vehicle;


import com.fleetmanagement.data.RouteData;

import java.util.List;

public class VehiclePlanData {

    private String plate;

    private List<RouteData> route;

    public VehiclePlanData() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public List<RouteData> getRoute() {
        return route;
    }

    public void setRoute(List<RouteData> route) {
        this.route = route;
    }
}
