package com.fleetmanagement.converter.impl;

import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Qualifier("vehicleDataModelConverter")
public class VehicleDataModelConverter implements Converter<VehicleDataList, List<Vehicle>> {

    private static String plateNumberPattern = "[0-8][0-9][a-zA-Z]{1,3}[0-9]{2,5}";

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean violation(VehicleDataList vehicleDataList) {
        return vehicleDataList.getVehicles().stream().anyMatch(vehicleData -> !Pattern.matches(plateNumberPattern, vehicleData.getPlateNumber()));
    }

    @Override
    public List<Vehicle> convert(VehicleDataList data) {
        if (violation(data)) {
            throw new DataConversionException(messageSource.getMessage("vehicle.plate.number.incorrect", null, Locale.ENGLISH));
        }
        List<String> plateNumberList = data.getVehicles().stream()
                .map(VehicleDataList.VehicleData::getPlateNumber).collect(Collectors.toList());
        return plateNumberList.stream().map(Vehicle::new).collect(Collectors.toList());
    }
}
