package com.fleetmanagement.converter.impl;


import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.converter.UnloadCalculation;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


@Component
@Qualifier("routeModelDataConverter")
public class RouteModelDataConverter implements ReverseConverter<List<Route>, RoutePlanData> {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public RoutePlanData convert(List<Route> routes) {
        RoutePlanData routePlanData = new RoutePlanData();
        List<RouteData> routeDataList = new LinkedList<>();
        routePlanData.setPlate(routes.iterator().next().getVehicle().getPlateNumber());
        populateVehicleRouteData(routes, routeDataList);
        routePlanData.setRoute(routeDataList);
        return routePlanData;
    }

    private void populateVehicleRouteData(List<Route> routes, List<RouteData> routeDataList) {
        routes.forEach(route -> {
            RouteData routeData = new RouteData();
            routeData.setDeliveryPoint(route.getDeliveryPoint().getId());
            List<RouteData.ShipmentInformation> shipmentInformations = new LinkedList<>();
            populateRouteShipmentInfo(route, shipmentInformations);
            routeData.setDeliveries(shipmentInformations);
            routeDataList.add(routeData);
        });
    }

    private void populateRouteShipmentInfo(Route route, List<RouteData.ShipmentInformation> shipmentInformations) {
        List<Shipment> shipments = new LinkedList<>();
        route.getDeliveries().forEach(shipment -> {
            UnloadCalculation.ShipmentUnloadCalculation unloadCalculation = shipment.getUnloadCalculationMethod();
            RouteData.ShipmentInformation shipmentInformation = new RouteData.ShipmentInformation();
            shipment.setStatus(unloadCalculation.calculateUnloading(route.getDeliveryPoint()));
            shipmentInformation.setBarcode(shipment.getBarcode());
            shipmentInformation.setState(shipment.getStatus());
            shipmentInformations.add(shipmentInformation);
            shipments.add(shipment);
        });
        shipmentRepository.saveAll(shipments);
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }
}
