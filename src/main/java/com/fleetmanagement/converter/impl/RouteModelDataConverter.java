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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


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
        route.getDeliveries().forEach(shipment -> {
            UnloadCalculation.ShipmentUnloadCalculation unloadCalculation = shipment.getUnloadCalculationMethod();
            RouteData.ShipmentInformation shipmentInformation = populateShipmentInformation(route, shipment, unloadCalculation);
            shipmentInformations.add(shipmentInformation);
        });
        shipmentRepository.saveAll(route.getDeliveries());

        List<String> existingBagsInRoute = route.getDeliveries().stream().filter(shipment -> shipment.getBarcode().
                startsWith("C")).map(Shipment::getBarcode).collect(Collectors.toList());
        List<Shipment> bagsOutOfRoute = !existingBagsInRoute.isEmpty() ? getBagShipmentsOutOfRoute(existingBagsInRoute) : Collections.emptyList();
        if (!bagsOutOfRoute.isEmpty()) {
            shipmentRepository.saveAll(bagsOutOfRoute);
        }
    }

    private List<Shipment> getBagShipmentsOutOfRoute(List<String> existingBagsInRoute) {
        List<Shipment> bagList = shipmentRepository.findByBarcodeLike("C%");
        List<Shipment> bagsOutOfRoute = bagList.stream().filter(shipment -> !existingBagsInRoute.
                contains(shipment.getBarcode())).collect(Collectors.toList());
        bagsOutOfRoute.forEach(shipment -> {
            UnloadCalculation.ShipmentUnloadCalculation unloadCalculation = shipment.getUnloadCalculationMethod();
            shipment.setStatus(unloadCalculation.calculateUnloading(null));
        });
        return bagsOutOfRoute;
    }

    private RouteData.ShipmentInformation populateShipmentInformation(Route route, Shipment shipment, UnloadCalculation.ShipmentUnloadCalculation unloadCalculation) {
        RouteData.ShipmentInformation shipmentInformation = new RouteData.ShipmentInformation();
        shipment.setStatus(unloadCalculation.calculateUnloading(route.getDeliveryPoint()));
        shipmentInformation.setBarcode(shipment.getBarcode());
        shipmentInformation.setState(shipment.getStatus());
        return shipmentInformation;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }
}
