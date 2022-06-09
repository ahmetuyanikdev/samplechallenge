package com.fleetmanagement.test.service;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.service.DeliveryPointService;
import com.fleetmanagement.service.impl.ShipmentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ShipmentServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private ShipmentServiceImpl shipmentService;

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
    public void test_saveShipments() {
        List<DeliveryPoint> deliveryPoints = deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        List<Shipment> shipments = shipmentService.saveShipments(shipmentDataList);
        Assert.assertEquals(shipments.get(0).getBarcode(), "C725799");
        Assert.assertEquals(shipments.get(0).getDeliveryPoint().getId(), deliveryPoints.get(0).getId());
    }
}
