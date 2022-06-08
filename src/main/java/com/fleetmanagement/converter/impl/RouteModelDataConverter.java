package com.fleetmanagement.converter.impl;


import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.converter.UnloadCalculation;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
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
        postUpdateShipmentUnloading(route);
    }

    private void postUpdateShipmentUnloading(Route route) {
        List<Shipment> postUpdateUnloadShipments = new LinkedList<>();
        updatePostUnloadingStatusesForRelatedBags(route, postUpdateUnloadShipments);
        updatePostUnloadingStatusesForRelatedPacks(route, postUpdateUnloadShipments);
        shipmentRepository.saveAll(postUpdateUnloadShipments);
    }

    private void updatePostUnloadingStatusesForRelatedPacks(Route route, List<Shipment> postUpdateUnloadShipments) {
        List<Bag> bagShipments = route.getDeliveries().stream().
                filter(shipment -> shipment.getClass().equals(Bag.class)).
                map(shipment -> (Bag) shipment).
                collect(Collectors.toList());
        bagShipments.forEach(shipment -> {
            Set<Package> packages = shipment.getPackages();
            packages.forEach(pack -> {
                UnloadCalculation.PostUpdateShipmentCalculation postUpdateShipmentCalculation = pack.getPostUpdateCalculationMethod();
                pack.setStatus(postUpdateShipmentCalculation.calculatePostUpdateUnloading(route.getDeliveryPoint()));
                postUpdateUnloadShipments.add(pack);
            });
        });
    }

    private void updatePostUnloadingStatusesForRelatedBags(Route route, List<Shipment> postUpdateUnloadShipments) {
        List<Package> packShipments = route.getDeliveries().stream().
                filter(shipment -> shipment.getClass().equals(Package.class)).
                map(shipment -> (Package) shipment).filter(pack -> Objects.nonNull(pack.getBag())).
                collect(Collectors.toList());
        packShipments.forEach(shipment -> {
            Bag bag = shipment.getBag();
            UnloadCalculation.PostUpdateShipmentCalculation postUpdateShipmentCalculation = bag.getPostUpdateCalculationMethod();
            bag.setStatus(postUpdateShipmentCalculation.calculatePostUpdateUnloading(route.getDeliveryPoint()));
            postUpdateUnloadShipments.add(bag);
        });
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
