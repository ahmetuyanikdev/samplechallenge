package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.model.DeliveryPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("deliveryPointModelDataConverter")
public class DeliveryPointModelDataConverter implements ReverseConverter<List<DeliveryPoint>, DeliveryPointDataList> {

    @Override
    public DeliveryPointDataList convert(List<DeliveryPoint> deliveryPoints) {
        DeliveryPointDataList dataList = new DeliveryPointDataList();
        List<DeliveryPointDataList.DeliveryPointData> deliveryPointDataList = new LinkedList<>();
        deliveryPointDataList = deliveryPoints.stream().map(DeliveryPointDataList.DeliveryPointData::new).collect(Collectors.toList());
        dataList.setDeliveryPoints(deliveryPointDataList);
        return dataList;
    }
}
