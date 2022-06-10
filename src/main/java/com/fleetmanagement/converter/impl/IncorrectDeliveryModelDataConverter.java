package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.IncorrectDeliveryDataList;
import com.fleetmanagement.model.IncorrectDelivery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("incorrectDeliveryModelDataConverter")
public class IncorrectDeliveryModelDataConverter implements Converter<List<IncorrectDelivery>, IncorrectDeliveryDataList> {

    @Override
    public IncorrectDeliveryDataList convert(List<IncorrectDelivery> incorrectDeliveries) {
        IncorrectDeliveryDataList incorrectDeliveryDataList = new IncorrectDeliveryDataList();
        incorrectDeliveryDataList.setDeliveryPoint(incorrectDeliveries.iterator().next().getDeliveryPointId());
        List<IncorrectDeliveryDataList.ShipmentData> shipmentData = incorrectDeliveries.stream().map(IncorrectDelivery::getBarcode).map(IncorrectDeliveryDataList.ShipmentData::new)
                .collect(Collectors.toList());
        incorrectDeliveryDataList.setDeliveries(shipmentData);
        return incorrectDeliveryDataList;
    }
}
