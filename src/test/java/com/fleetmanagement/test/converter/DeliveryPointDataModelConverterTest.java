package com.fleetmanagement.test.converter;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.converter.impl.DeliveryPointDataModelConverter;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.model.DeliveryPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryPointDataModelConverterTest {

    @InjectMocks
    private DeliveryPointDataModelConverter deliveryPointDataModelConverter;

    @Mock
    private MessageSource messageSource;

    private DeliveryPointDataList deliveryPointDataList;

    private List<DeliveryPoint> deliveryPoints;

    @Before
    public void setup() {
        deliveryPointDataList = new DeliveryPointDataList();
        deliveryPoints = new LinkedList<>();
        DeliveryPointDataList.DeliveryPointData deliveryPointData = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData.setId(1);
        deliveryPointData.setType(DeliveryPointType.Branch.name());
        deliveryPointDataList.setDeliveryPoints(Collections.singletonList(deliveryPointData));
    }

    @Test
    public void test1_convertDeliveryPointData_success() {
        List<DeliveryPoint> deliveryPoints = deliveryPointDataModelConverter.convert(deliveryPointDataList);
        Assert.assertEquals(deliveryPoints.get(0).getType(), DeliveryPointType.Branch.name());
    }

    @Test
    public void test2_convertDeliveryPointData_fail() {
        Mockito.when(messageSource.getMessage("delivery-point.data.violation", null, Locale.ENGLISH)).thenReturn("Data invalid");
        deliveryPointDataList.getDeliveryPoints().get(0).setType("");
        List<DeliveryPoint> deliveryPoints = new LinkedList<>();
        try {
            deliveryPoints = deliveryPointDataModelConverter.convert(deliveryPointDataList);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), DataConversionException.class);
            Assert.assertEquals(e.getMessage(), "Data invalid");
        } finally {
            Assert.assertTrue(deliveryPoints.isEmpty());
        }
    }
}
