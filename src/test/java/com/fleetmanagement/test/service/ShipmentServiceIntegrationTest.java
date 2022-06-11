package com.fleetmanagement.test.service;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.shipment.ShipmentAssignmentDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.exception.DataConversionException;
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

    private ShipmentAssignmentDataList shipmentAssignmentDataList;

    @Before
    public void setup() {
        shipmentDataList = new ShipmentDataList();
        deliveryPointDataList = new DeliveryPointDataList();
        shipmentAssignmentDataList = new ShipmentAssignmentDataList();

        DeliveryPointDataList.DeliveryPointData deliveryPointData = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData.setId(1);
        deliveryPointData.setType(DeliveryPointType.Branch.name());
        deliveryPointDataList.setDeliveryPoints(Collections.singletonList(deliveryPointData));

        ShipmentDataList.ShipmentData shipmentData1 = new ShipmentDataList.ShipmentData();
        shipmentData1.setBarcode("C725799");
        shipmentData1.setDeliveryPoint(deliveryPointData.getId());

        ShipmentDataList.ShipmentData shipmentData2 = new ShipmentDataList.ShipmentData();
        shipmentData2.setBarcode("P8988000122");
        shipmentData2.setDeliveryPoint(deliveryPointData.getId());
        shipmentData2.setVolumetricWeight(20);

        List<ShipmentDataList.ShipmentData> dataList = new LinkedList<>();
        dataList.add(shipmentData1);
        dataList.add(shipmentData2);
        shipmentDataList.setShipments(dataList);

        ShipmentAssignmentDataList.ShipmentAssignment shipmentAssignment = new ShipmentAssignmentDataList.ShipmentAssignment();
        shipmentAssignment.setBarcode(shipmentData2.getBarcode());
        shipmentAssignment.setBagBarcode(shipmentData1.getBarcode());
        shipmentAssignmentDataList.setShipmentAssignments(Collections.singletonList(shipmentAssignment));
    }

    @Test
    public void test_01_saveShipments() {
        List<DeliveryPoint> deliveryPoints = deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        List<Shipment> shipments = shipmentService.saveShipments(shipmentDataList);
        Assert.assertEquals(shipments.get(0).getBarcode(), "C725799");
        Assert.assertEquals(shipments.get(0).getDeliveryPoint().getId(), deliveryPoints.get(0).getId());
    }

    @Test
    public void test_02_getAllShipments() {
        ShipmentDataList shipmentDataList = shipmentService.getAllShipments();
        Assert.assertFalse(shipmentDataList.getShipments().isEmpty());
        Assert.assertTrue(shipmentDataList.getShipments().stream().map(ShipmentDataList.ShipmentData::getBarcode).
                anyMatch(barcode -> barcode.equalsIgnoreCase("C725799")));
    }

    @Test
    public void test_03_saveShipmentAssignment_fail() {
        ShipmentDataList dataList = new ShipmentDataList();
        shipmentAssignmentDataList.getShipmentAssignments().get(0).setBagBarcode("");
        try {
            dataList = shipmentService.assignShipments(shipmentAssignmentDataList);
        } catch (DataConversionException e) {
        } finally {
            Assert.assertEquals(dataList.getShipments(),null);
        }
    }

    @Test
    public void test_04_saveShipmentAssignment_fail() {
        ShipmentDataList dataList = new ShipmentDataList();
        shipmentAssignmentDataList.getShipmentAssignments().get(0).setBagBarcode("");
        try {
            dataList = shipmentService.assignShipments(shipmentAssignmentDataList);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), DataConversionException.class);
        } finally {
            Assert.assertEquals(dataList.getShipments(),null);
        }
    }
}
