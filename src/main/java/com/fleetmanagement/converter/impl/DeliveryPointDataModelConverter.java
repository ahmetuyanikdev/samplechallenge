package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.model.DeliveryPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("deliveryPointDataModelConverter")
public class DeliveryPointDataModelConverter implements Converter<DeliveryPointDataList, List<DeliveryPoint>> {

    @Override
    public List<DeliveryPoint> convert(DeliveryPointDataList deliveryPointDataList) {
        List<DeliveryPoint> deliveryPoints = deliveryPointDataList.getDeliveryPoints().stream()
                .map(DeliveryPoint::new).collect(Collectors.toList());
        return deliveryPoints;
    }
}
