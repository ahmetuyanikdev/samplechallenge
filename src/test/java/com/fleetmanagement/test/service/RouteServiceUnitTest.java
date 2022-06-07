package com.fleetmanagement.test.service;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.repository.RouteRepository;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.service.impl.RouteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)

public class RouteServiceUnitTest {

    @InjectMocks
    private RouteServiceImpl routeService;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private DeliveryPointRepository deliveryPointRepository;

    @Mock
    private Converter<RoutePlanData, List<Route>> converter;

    @Mock
    private ReverseConverter<List<Route>, RoutePlanData> reverseConverter;

    private RoutePlanData routePlanData;

    private List<Route> routes;

    private Shipment shipment1;

    private Route route1;

    private DeliveryPoint deliveryPoint;

    @Before
    public void setup() {

        routePlanData = new RoutePlanData();
        routes = new LinkedList<>();
        routePlanData.setPlate("34ABC001");

        List<RouteData> routeDataList = new LinkedList<>();
        RouteData routeData1 = new RouteData();

        RouteData.ShipmentInformation shipmentInformation1 = new RouteData.ShipmentInformation();
        shipmentInformation1.setState(1);
        shipmentInformation1.setBarcode("P7988000121");

        RouteData.ShipmentInformation shipmentInformation2 = new RouteData.ShipmentInformation();
        shipmentInformation2.setState(2);
        shipmentInformation2.setBarcode("P9988000126");

        List<RouteData.ShipmentInformation> shipmentInformations1 = new LinkedList<>();
        shipmentInformations1.add(shipmentInformation1);
        shipmentInformations1.add(shipmentInformation2);

        routeData1.setDeliveryPoint(1);
        routeData1.setDeliveries(shipmentInformations1);
        routeDataList.add(routeData1);

        routePlanData.setRoute(routeDataList);

        route1 = new Route();
        deliveryPoint = new DeliveryPoint();
        deliveryPoint.setId(1);
        deliveryPoint.setType("Branch");

        shipment1 = new Shipment();
        shipment1.setRoute(route1);
        shipment1.setStatus(1);
        shipment1.setBarcode("P7988000121");
        shipment1.setDeliveryPoint(deliveryPoint);

        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("34ABC001");

        route1.setId(0);
        route1.setDeliveries(Collections.singleton(shipment1));
        route1.setVehicle(vehicle);
        route1.setDeliveryPoint(deliveryPoint);

        routes.add(route1);
    }


    @Test
    public void test_saveRoutes_success() {
        Mockito.when(converter.convert(routePlanData)).thenReturn(routes);
        Mockito.when(routeRepository.saveAll(routes)).thenReturn(routes);
        Mockito.when(deliveryPointRepository.save(deliveryPoint)).thenReturn(deliveryPoint);
        List<Route> routes = routeService.saveRoutes(routePlanData);
        Assert.assertTrue(routes.stream().anyMatch(route -> route.getVehicle().getPlateNumber().equals("34ABC001")));
        Mockito.verify(routeRepository, Mockito.times(1)).saveAll(routes);
    }

    @Test
    public void test_saveRoutes_fail() {
        Mockito.when(converter.convert(routePlanData)).thenReturn(routes);
        Mockito.when(routeRepository.saveAll(routes)).thenReturn(routes);
        Mockito.when(shipmentRepository.saveAll(routes.get(0).getDeliveries())).
                thenThrow(NullPointerException.class);
        try {
            List<Route> routes = routeService.saveRoutes(routePlanData);
        } catch (Exception e) {

        } finally {
            Mockito.verify(deliveryPointRepository, Mockito.times(0)).save(deliveryPoint);
        }

    }

}
