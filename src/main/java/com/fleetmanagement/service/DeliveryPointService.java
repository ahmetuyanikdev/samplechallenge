package com.fleetmanagement.service;

import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.model.DeliveryPoint;

import java.util.List;

public interface DeliveryPointService {
    List<DeliveryPoint> saveDeliveryPoints(DeliveryPointDataList deliveryPointDataList);
    DeliveryPointDataList getAllDeliveryPoints();
}
