package com.fleetmanagement.test.service;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.service.DeliveryPointService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Collections;
import java.util.List;


public class DeliveryPointServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private DeliveryPointService deliveryPointService;

    private DeliveryPointDataList deliveryPointDataList;

    @Before
    public void setup() {
        deliveryPointDataList = new DeliveryPointDataList();
        DeliveryPointDataList.DeliveryPointData deliveryPointData = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData.setType(DeliveryPointType.Branch.name());
        deliveryPointData.setId(DeliveryPointType.Branch.getValue());
        deliveryPointDataList.setDeliveryPoints(Collections.singletonList(deliveryPointData));
    }

    @Test
    public void test1_saveDeliveryPoints() {
        List<DeliveryPoint> deliveryPoints = deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        Assert.assertEquals(deliveryPoints.get(0).getType(), DeliveryPointType.Branch.name());
    }

    @Test
    public void test2_getAllDeliveryPoints() {
        DeliveryPointDataList deliveryPointDataList = deliveryPointService.getAllDeliveryPoints();
        Assert.assertEquals(deliveryPointDataList.getDeliveryPoints().get(0).getType(),DeliveryPointType.Branch.name());
    }

}
