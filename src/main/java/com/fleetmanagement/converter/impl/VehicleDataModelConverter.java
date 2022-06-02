package com.fleetmanagement.converter.impl;

import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.converter.Converter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("vehicleDataModelConverter")
public class VehicleDataModelConverter implements Converter<VehicleDataList, List<Vehicle>> {

    @Override
    public List<Vehicle> convert(VehicleDataList data) {
        List<String> plateNumberList = data.getVehicles().stream()
                .map(VehicleDataList.VehicleData::getPlateNumber).collect(Collectors.toList());
        return plateNumberList.stream().map(Vehicle::new).collect(Collectors.toList());
    }
}
