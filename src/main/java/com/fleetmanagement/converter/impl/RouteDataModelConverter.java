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
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("routeDataModelConverter")
public class RouteDataModelConverter implements Converter<RoutePlanData, List<Route>> {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DeliveryPointRepository deliveryPointRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public static ShipmentLoadStatus shipmentStatus = (shipment) -> shipment.getClass().equals(Package.class) ?
            (Objects.nonNull(((Package) shipment).getBag()) ?
                    PackageStatus.Loaded_Into_Bag.getValue() : PackageStatus.Loaded.getValue()) :
            BagStatus.Loaded.getValue();

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

    private void populateShipmentStatusInfo(Shipment sh, Route route) {
        sh.setStatus(shipmentStatus.status(sh));
        Set<Route> routes = new HashSet<>();
        routes.addAll(sh.getRoutes());
        routes.add(route);
        sh.setRoutes(routes);
    }

    interface ShipmentLoadStatus {
        int status(Shipment shipment);
    }

    private void populateShipments(RouteData routeData, Route route) {
        Set<Shipment> shipmentSet = new HashSet<>(getShipments(routeData));
        route.setDeliveries(shipmentSet);
        shipmentSet.forEach(shipment -> {
            populateShipmentStatusInfo(shipment, route);
        });
    }

    private void populateVehicleInfo(RoutePlanData routePlanData, Route route) {
        Vehicle vehicle = vehicleRepository.getVehicleByPlateNumber(routePlanData.getPlate());
        route.setVehicle(vehicle);
    }

    private void populateDeliveryPointInfo(RouteData routeData, Route route) {
        DeliveryPoint deliveryPoint = deliveryPointRepository.findById(routeData.getDeliveryPoint());
        route.setDeliveryPoint(deliveryPoint);
        Set<Route> routes = deliveryPoint.getRoutes();
        routes.add(route);
        deliveryPoint.setRoutes(routes);
    }

    private List<Shipment> getShipments(RouteData routeData) {
        Set<String> shipmentBarcodes = routeData.getDeliveries().stream().
                map(RouteData.ShipmentInformation::getBarcode).collect(Collectors.toSet());
        return shipmentRepository.findAllById(shipmentBarcodes);
    }

    public void setDeliveryPointRepository(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
}
