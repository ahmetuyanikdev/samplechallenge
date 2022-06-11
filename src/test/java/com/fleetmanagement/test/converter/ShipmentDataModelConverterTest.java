package com.fleetmanagement.test.converter;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.converter.impl.ShipmentDataModelConverter;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.repository.DeliveryPointRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ShipmentDataModelConverterTest {

    @InjectMocks
    ShipmentDataModelConverter shipmentDataModelConverter;

    @Mock
    DeliveryPointRepository deliveryPointRepository;

    @Mock
    MessageSource messageSource;

    ShipmentDataList shipmentDataList;

    List<Shipment> shipments;

    @Before
    public void setup() {
        shipmentDataList = new ShipmentDataList();
        shipments = new LinkedList<>();
        ShipmentDataList.ShipmentData shipmentData = new ShipmentDataList.ShipmentData();
        shipmentData.setDeliveryPoint(1);
        shipmentData.setVolumetricWeight(20);
        shipmentData.setBarcode("P9988000129");
        shipmentDataList.setShipments(Collections.singletonList(shipmentData));
    }

    @Test
    public void test1_convertShipmentData_success() {
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setId(DeliveryPointType.Branch.getValue());
        deliveryPoint.setType(DeliveryPointType.Branch.name());
        Mockito.when(deliveryPointRepository.findById(1)).thenReturn(Optional.of(deliveryPoint));
        List<Shipment> shipments = shipmentDataModelConverter.convert(shipmentDataList);
        Assert.assertEquals(shipments.get(0).getBarcode(), "P9988000129");
    }

    @Test
    public void test2_convertShipmentData_fail() {
        Mockito.when(messageSource.getMessage("shipment.data.violation", null, Locale.ENGLISH)).thenReturn("Data invalid");
        shipmentDataList.getShipments().get(0).setBarcode("");
        List<Shipment> shipments = new LinkedList<>();
        try {
            shipments = shipmentDataModelConverter.convert(shipmentDataList);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), DataConversionException.class);
            Assert.assertEquals(e.getMessage(), "Data invalid");
        }
    }
}
