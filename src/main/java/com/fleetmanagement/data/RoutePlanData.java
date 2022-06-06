package com.fleetmanagement.data;

import java.util.List;

public class RoutePlanData {

    private String plate;

    private List<RouteData> route;

    public RoutePlanData() {
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
