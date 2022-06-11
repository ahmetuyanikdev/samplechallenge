package com.fleetmanagement.test.service;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.service.DeliveryPointService;
import com.fleetmanagement.service.RouteService;
import com.fleetmanagement.service.ShipmentService;
import com.fleetmanagement.service.VehicleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RouteServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private RouteService routeService;

    @Autowired
    private DeliveryPointService deliveryPointService;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private VehicleService vehicleService;

    private RoutePlanData routePlanData;

    private DeliveryPointDataList deliveryPointDataList;

    private ShipmentDataList shipmentDataList;

    private VehicleDataList vehicleDataList;

    @Before
    public void setup() {

        routePlanData = new RoutePlanData();
        routePlanData.setPlate("34TL34");
        deliveryPointDataList = new DeliveryPointDataList();
        shipmentDataList = new ShipmentDataList();
        vehicleDataList = new VehicleDataList();

        /*vehicle data*/
        VehicleDataList.VehicleData vehicleData = new VehicleDataList.VehicleData();
        vehicleData.setPlateNumber("34TL34");
        vehicleDataList.setVehicles(Collections.singletonList(vehicleData));

        /*delivery point data*/
        DeliveryPointDataList.DeliveryPointData deliveryPointData = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData.setId(DeliveryPointType.Branch.getValue());
        deliveryPointData.setType(DeliveryPointType.Branch.name());
        deliveryPointDataList.setDeliveryPoints(Collections.singletonList(deliveryPointData));

        /*shipment data*/
        ShipmentDataList.ShipmentData shipmentData1 = new ShipmentDataList.ShipmentData();
        shipmentData1.setDeliveryPoint(deliveryPointData.getId());
        shipmentData1.setBarcode("P7988000121");
        shipmentData1.setVolumetricWeight(10);
        ShipmentDataList.ShipmentData shipmentData2 = new ShipmentDataList.ShipmentData();
        shipmentData2.setDeliveryPoint(deliveryPointData.getId());
        shipmentData2.setBarcode("P7988000122");
        shipmentData2.setVolumetricWeight(20);

        List<ShipmentDataList.ShipmentData> dataList = new LinkedList<>();
        dataList.add(shipmentData1);
        dataList.add(shipmentData2);

        shipmentDataList.setShipments(dataList);

        /*route data*/
        RouteData routeData = new RouteData();
        RouteData.ShipmentInformation shipmentInformation1 = new RouteData.ShipmentInformation();
        shipmentInformation1.setBarcode(shipmentData1.getBarcode());

        RouteData.ShipmentInformation shipmentInformation2 = new RouteData.ShipmentInformation();
        shipmentInformation2.setBarcode(shipmentData2.getBarcode());

        List<RouteData.ShipmentInformation> shipmentInformations = new LinkedList<>();
        shipmentInformations.add(shipmentInformation1);
        shipmentInformations.add(shipmentInformation2);
        routeData.setDeliveries(shipmentInformations);
        routeData.setDeliveryPoint(deliveryPointData.getId());
        routePlanData.setRoute(Collections.singletonList(routeData));
    }

    @Test
    public void test1_saveVehicle() {
        List<Vehicle> vehicles = vehicleService.saveVehicles(vehicleDataList);
        Assert.assertEquals(vehicles.get(0).getPlateNumber(), "34TL34");
    }

    @Test
    public void test2_deliveryPoint() {
        List<DeliveryPoint> deliveryPoints = deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        Assert.assertEquals(deliveryPoints.get(0).getType(), (DeliveryPointType.Branch.name()));

    }

    @Test
    public void test3_saveRoute() {

        List<Shipment> shipments = shipmentService.saveShipments(shipmentDataList);
        Assert.assertEquals(shipments.size(), 2);
        Assert.assertEquals(shipments.get(1).getBarcode(), "P7988000122");

        List<Route> routes = routeService.saveRoutes(routePlanData);
        Assert.assertEquals(routes.size(), 1);
        Assert.assertEquals(routes.get(0).getVehicle().getPlateNumber(), "34TL34");
        Assert.assertEquals(routes.get(0).getDeliveries().size(), 2);
        Shipment shipment = (Shipment) routes.get(0).getDeliveries().toArray()[1];
        List<String> barcodes = new LinkedList<>();
        barcodes.add("P7988000122");
        barcodes.add("P7988000121");
        Assert.assertTrue(barcodes.contains(shipment.getBarcode()));
    }

}
