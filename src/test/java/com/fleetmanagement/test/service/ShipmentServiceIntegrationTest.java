package com.fleetmanagement.test.service;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.service.DeliveryPointService;
import com.fleetmanagement.service.ShipmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ShipmentServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private DeliveryPointService deliveryPointService;

    private ShipmentDataList shipmentDataList;

    private DeliveryPointDataList deliveryPointDataList;

    @Before
    public void setup() {
        shipmentDataList = new ShipmentDataList();
        deliveryPointDataList = new DeliveryPointDataList();
        DeliveryPointDataList.DeliveryPointData deliveryPointData = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData.setId(1);
        deliveryPointData.setType(DeliveryPointType.Branch.name());
        deliveryPointDataList.setDeliveryPoints(Collections.singletonList(deliveryPointData));

        ShipmentDataList.ShipmentData shipmentData = new ShipmentDataList.ShipmentData();
        shipmentData.setBarcode("C725799");
        shipmentData.setDeliveryPoint(deliveryPointData.getId());
        List<ShipmentDataList.ShipmentData> dataList = new LinkedList<>();
        dataList.add(shipmentData);
        shipmentDataList.setShipments(dataList);
    }

    @Test
    public void test_0_saveShipments(){
        List<DeliveryPoint> deliveryPoints = deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        List<Shipment> shipments = shipmentService.saveShipments(shipmentDataList);
        Assert.assertEquals(shipments.get(0).getBarcode(), "C725799");
        Assert.assertEquals(shipments.get(0).getDeliveryPoint().getId(), deliveryPoints.get(0).getId());
    }

    @Test
    public void test_1_getAllShipments(){
        ShipmentDataList shipmentDataList = shipmentService.getAllShipments();
        Assert.assertFalse(shipmentDataList.getShipments().isEmpty());
        Assert.assertTrue(shipmentDataList.getShipments().stream().map(ShipmentDataList.ShipmentData::getBarcode).
                anyMatch(barcode->barcode.equalsIgnoreCase("C725799")));
    }
}
