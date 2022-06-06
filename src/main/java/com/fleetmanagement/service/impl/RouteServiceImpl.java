package com.fleetmanagement.service.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.repository.RouteRepository;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    @Qualifier("routeDataModelConverter")
    private Converter<RoutePlanData, List<Route>> converter;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public List<Route> saveRoutes(RoutePlanData routePlanData) {
        List<Route> routes = converter.convert(routePlanData);
        routes = routeRepository.saveAll(routes);
        updateShipmentsStatuses(routes);
        return routes;
    }

    private void updateShipmentsStatuses(List<Route> routes) {
        routes.forEach(route -> {
            shipmentRepository.saveAll(route.getDeliveries());
        });
    }

    @Override
    public RoutePlanData unloadDeliveries(List<Route> routes) {
        return null;
    }

    public void setConverter(Converter<RoutePlanData, List<Route>> converter) {
        this.converter = converter;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }
}
