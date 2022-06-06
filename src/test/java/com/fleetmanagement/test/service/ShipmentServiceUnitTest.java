package com.fleetmanagement.test.service;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.shipment.ShipmentAssignmentDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.service.impl.ShipmentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ShipmentServiceUnitTest {

    private Logger logger = LoggerFactory.getLogger(ShipmentServiceUnitTest.class);

    @InjectMocks
    private ShipmentServiceImpl itemService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private Converter<ShipmentDataList, List<Shipment>> converter;

    @Mock
    private ReverseConverter<List<Shipment>, ShipmentDataList> reverseConverter;

    @Mock
    private Converter<ShipmentAssignmentDataList, List<Shipment>> itemAssignmentConverter;

    private ShipmentDataList shipmentDataList;

    private List<Shipment> shipments;

    private ShipmentAssignmentDataList shipmentAssignmentDataList;

    @Before
    public void setup() {
        shipmentDataList = new ShipmentDataList();
        shipments = new LinkedList<>();
        shipmentAssignmentDataList = new ShipmentAssignmentDataList();

        List<ShipmentDataList.ShipmentData> dataList = new LinkedList<>();

        Bag bag1 = new Bag();
        Package pack1 = new Package();
        DeliveryPoint branch = new DeliveryPoint();

        branch.setId(1);
        branch.setType("Branch");
        bag1.setBarcode("C725799");
        bag1.setDeliveryPoint(branch);

        pack1.setBarcode("P8988000120");
        pack1.setDeliveryPoint(branch);
        pack1.setVolumetricWeight(9);
        pack1.setBag(bag1);

        shipments.add(bag1);
        shipments.add(pack1);

        ShipmentDataList.ShipmentData shipmentData1 = new ShipmentDataList.ShipmentData();
        shipmentData1.setBarcode("C725799");
        shipmentData1.setDeliveryPoint(1);

        dataList.add(shipmentData1);

        ShipmentDataList.ShipmentData shipmentData2 = new ShipmentDataList.ShipmentData();
        shipmentData2.setBarcode("P8988000120");
        shipmentData2.setDeliveryPoint(1);
        shipmentData2.setVolumetricWeight(9);

        dataList.add(shipmentData2);
        shipmentDataList.setShipments(dataList);

        ShipmentAssignmentDataList.ShipmentAssignment shipmentAssignment1 = new ShipmentAssignmentDataList.ShipmentAssignment();
        shipmentAssignment1.setBarcode("P8988000120");
        shipmentAssignment1.setBagBarcode("C725799");
        List<ShipmentAssignmentDataList.ShipmentAssignment> shipmentAssignments = new LinkedList<>();
        shipmentAssignments.add(shipmentAssignment1);
        shipmentAssignmentDataList.setShipmentAssignments(shipmentAssignments);
    }

    @Test
    public void test_getAllShipment_success() {
        Mockito.when(shipmentRepository.findAll()).thenReturn(shipments);
        Mockito.when(reverseConverter.convert(shipments)).thenReturn(shipmentDataList);
        ShipmentDataList list = itemService.getAllShipments();
        Assert.assertEquals(list.getShipments().size(), 2);
        Assert.assertTrue(list.getShipments().stream().anyMatch(i -> i.getBarcode().equalsIgnoreCase("P8988000120")));
    }

    @Test
    public void test_getAllShipments_fail() {
        Mockito.when(shipmentRepository.findAll()).thenThrow(NullPointerException.class);
        try {
            itemService.getAllShipments();
        } catch (NullPointerException e) {
            logger.warn("---- Exception during test method invocation ");
        } finally {
            Mockito.verify(reverseConverter, Mockito.never()).convert(shipments);
        }
    }

    @Test
    public void test_saveShipment_success() {
        Mockito.when(converter.convert(shipmentDataList)).thenReturn(shipments);
        Mockito.when(shipmentRepository.saveAll(shipments)).thenReturn(shipments);
        Assert.assertFalse(itemService.saveShipments(shipmentDataList).isEmpty());
    }

    @Test
    public void test_saveShipment_fail() {
        Mockito.when(converter.convert(shipmentDataList)).thenThrow(NumberFormatException.class);
        try {
            itemService.saveShipments(shipmentDataList);
        } catch (Exception e) {
            logger.warn("---- Exception during test method invocation ");
        } finally {
            Mockito.verify(shipmentRepository, Mockito.never()).saveAll(shipments);
        }
    }

    @Test
    public void test_assignShipments_success() {
        Mockito.when(itemAssignmentConverter.convert(shipmentAssignmentDataList)).thenReturn(shipments);
        Mockito.when(shipmentRepository.saveAll(shipments)).thenReturn(shipments);
        Mockito.when(reverseConverter.convert(shipments)).thenReturn(shipmentDataList);
        Assert.assertTrue(itemService.assignShipments(shipmentAssignmentDataList).getShipments().stream().anyMatch(item -> item.getBarcode().equals("P8988000120")));
    }

}
