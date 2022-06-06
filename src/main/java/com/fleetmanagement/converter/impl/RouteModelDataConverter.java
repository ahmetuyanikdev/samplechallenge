package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.Route;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Qualifier("routeModelDataConverter")
public class RouteModelDataConverter implements ReverseConverter<List<Route>,RoutePlanData> {

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
            RouteData.ShipmentInformation shipmentInformation = new RouteData.ShipmentInformation();
            shipmentInformation.setBarcode(shipment.getBarcode());
            shipmentInformation.setState(shipment.getStatus());
            shipmentInformations.add(shipmentInformation);
        });
    }
}
