package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.model.DeliveryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Qualifier("deliveryPointDataModelConverter")
public class DeliveryPointDataModelConverter implements Converter<DeliveryPointDataList, List<DeliveryPoint>> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean violation(DeliveryPointDataList deliveryPointDataList) {
        boolean violation = deliveryPointDataList.getDeliveryPoints().stream().anyMatch(deliveryPointData -> (Objects.equals(deliveryPointData.getId(), 0) ||
                Objects.equals(deliveryPointData.getType(), "")));
        return violation;
    }

    @Override
    public List<DeliveryPoint> convert(DeliveryPointDataList deliveryPointDataList) {
        if (violation(deliveryPointDataList)) {
            throw new DataConversionException(messageSource.getMessage("delivery-point.data.violation", null, Locale.ENGLISH));
        }
        return deliveryPointDataList.getDeliveryPoints().stream()
                .map(DeliveryPoint::new).collect(Collectors.toList());
    }
}
