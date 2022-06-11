package com.fleetmanagement.data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class RoutePlanData {

    @NotNull(message = "{route.plate.number.missing}")
    @Pattern(regexp = "[0-8][0-9][a-zA-Z]{1,3}[0-9]{2,5}", message = "{vehicle.plate.number.incorrect}")
    private String plate;

    private List<@Valid RouteData> route;

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
