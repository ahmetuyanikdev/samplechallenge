package com.fleetmanagement.converter.impl;

import com.fleetmanagement.constant.BagStatus;
import com.fleetmanagement.constant.PackageStatus;
import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.exception.NoDataFoundException;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.IncorrectDelivery;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.repository.IncorrectDeliveryRepository;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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

    @Autowired
    private IncorrectDeliveryRepository incorrectDeliveryRepository;

    @Autowired
    private MessageSource messageSource;

    public static ShipmentLoadStatus shipmentStatus = (shipment) -> shipment.getClass().equals(Package.class) ?
            (Objects.nonNull(((Package) shipment).getBag()) ?
                    PackageStatus.Loaded_Into_Bag.getValue() : PackageStatus.Loaded.getValue()) :
            BagStatus.Loaded.getValue();

    @Override
    public boolean violation(RoutePlanData routePlanData) {
        return routePlanData.getPlate().equals("") ||
                routePlanData.getRoute().stream().anyMatch(routeData -> routeData.getDeliveryPoint() == 0) ||
                routePlanData.getRoute().stream().map(RouteData::getDeliveries).
                        anyMatch(shipmentInformations -> shipmentInformations.stream().
                                anyMatch(shipmentInformation -> shipmentInformation.getBarcode().equals("")));
    }

    @Override
    public List<Route> convert(RoutePlanData routePlanData) {
        if (violation(routePlanData)) {
            throw new DataConversionException(messageSource.getMessage("route.data.violation", null, Locale.ENGLISH));
        }
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
        detectAndSaveIncorrectlySendDeliveries(routeData, shipmentSet);
    }

    private void detectAndSaveIncorrectlySendDeliveries(RouteData routeData, Set<Shipment> shipmentSet) {
        Set<Shipment> incorrectlyLoadedShipments = shipmentSet.stream().
                filter(shipment -> shipment.getDeliveryPoint().getId() != routeData.getDeliveryPoint())
                .collect(Collectors.toSet());
        List<IncorrectDelivery> incorrectDeliveries = new LinkedList<>();
        incorrectlyLoadedShipments.forEach(shipment -> {
            IncorrectDelivery incorrectDelivery = IncorrectDelivery.getInstance(routeData.getDeliveryPoint(), shipment.getBarcode());
            incorrectDeliveries.add(incorrectDelivery);
        });
        incorrectDeliveryRepository.saveAll(incorrectDeliveries);
    }

    private void populateVehicleInfo(RoutePlanData routePlanData, Route route) {
        Vehicle vehicle = vehicleRepository.findById(routePlanData.getPlate()).orElseThrow(()
                -> new NoDataFoundException(messageSource.getMessage("route.vehicle.not-found", null, Locale.ENGLISH)));
        route.setVehicle(vehicle);
    }

    private void populateDeliveryPointInfo(RouteData routeData, Route route) {
        DeliveryPoint deliveryPoint = deliveryPointRepository.findById(routeData.getDeliveryPoint()).
                orElseThrow(() -> new NoDataFoundException(messageSource.getMessage("route.delivery-point.not-found",
                        null, Locale.ENGLISH)));
        route.setDeliveryPoint(deliveryPoint);
        Set<Route> routes = deliveryPoint.getRoutes();
        routes.add(route);
        deliveryPoint.setRoutes(routes);
    }

    private List<Shipment> getShipments(RouteData routeData) {
        Set<String> shipmentBarcodes = routeData.getDeliveries().stream().
                map(RouteData.ShipmentInformation::getBarcode).collect(Collectors.toSet());
        List<Shipment> shipments = shipmentRepository.findAllById(shipmentBarcodes);
        if (!shipments.stream().map(Shipment::getBarcode).collect(Collectors.toList()).containsAll(shipmentBarcodes)) {
            throw new NoDataFoundException(messageSource.getMessage("route.shipment.not-found", null, Locale.ENGLISH));
        }
        return shipmentRepository.findAllById(shipmentBarcodes);
    }

    public void setDeliveryPointRepository(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setIncorrectDeliveryRepository(IncorrectDeliveryRepository incorrectDeliveryRepository) {
        this.incorrectDeliveryRepository = incorrectDeliveryRepository;
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
}
