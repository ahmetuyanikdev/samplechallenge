package com.fleetmanagement.service;

import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.Route;

import java.util.List;

public interface RouteService {
     List<Route> saveRoutes(RoutePlanData routePlanData);
     RoutePlanData unloadDeliveries(List<Route> routes);
}
