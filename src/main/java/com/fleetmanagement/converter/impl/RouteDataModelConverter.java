package com.fleetmanagement.converter.impl;

import com.fleetmanagement.constant.BagStatus;
import com.fleetmanagement.constant.PackageStatus;
import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.service.DeliveryPointService;
import com.fleetmanagement.service.ShipmentService;
import com.fleetmanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("routeDataModelConverter")
public class RouteDataModelConverter implements Converter<RoutePlanData, List<Route>> {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DeliveryPointService deliveryPointService;

    @Autowired
    private ShipmentService shipmentService;

    @Override
    public List<Route> convert(RoutePlanData routePlanData) {
        List<Route> routes = new LinkedList<>();
        routePlanData.getRoute().forEach(routeData -> {
            Route route = Route.getInstance();
            populateDeliveryPointInfo(routeData, route);
            populateVehicleInfo(routePlanData, route);
            populateShipments(routeData, route);
            routes.add(route);
        });
        return routes;
    }

    private void populateShipmentStatusInfo(Shipment sh) {
        ShipmentLoadStatus shipmentStatus = (shipment) -> shipment.getClass().equals(Package.class) ?
                (Objects.nonNull(((Package) shipment).getBag()) ?
                        PackageStatus.Loaded_Into_Bag.getValue() : PackageStatus.Loaded.getValue()) :
                BagStatus.Loaded.getValue();
        sh.setStatus(shipmentStatus.status(sh));
    }

    interface ShipmentLoadStatus {
        int status(Shipment shipment);
    }

    private void populateShipments(RouteData routeData, Route route) {
        Set<Shipment> shipmentSet = new HashSet<>(getShipments(routeData));
        route.setDeliveries(shipmentSet);
        shipmentSet.forEach(this::populateShipmentStatusInfo);
    }

    private void populateVehicleInfo(RoutePlanData routePlanData, Route route) {
        Vehicle vehicle = vehicleService.getVehicleByPlateNumber(routePlanData.getPlate());
        route.setVehicle(vehicle);
    }

    private void populateDeliveryPointInfo(RouteData routeData, Route route) {
        DeliveryPoint deliveryPoint = deliveryPointService.getDeliveryPointById(routeData.getDeliveryPoint());
        route.setDeliveryPoint(deliveryPoint);
    }

    private List<Shipment> getShipments(RouteData routeData) {
        Set<String> shipmentBarcodes = routeData.getDeliveries().stream().
                map(RouteData.ShipmentInformation::getBarcode).collect(Collectors.toSet());
        return shipmentService.getShipmentByBarcodes(shipmentBarcodes);
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public DeliveryPointService getDeliveryPointService() {
        return deliveryPointService;
    }

    public void setDeliveryPointService(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

    public ShipmentService getShipmentService() {
        return shipmentService;
    }

    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }
}
