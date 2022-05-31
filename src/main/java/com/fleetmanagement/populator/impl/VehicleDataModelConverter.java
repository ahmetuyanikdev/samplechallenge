package com.fleetmanagement.populator.impl;

import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.populator.Converter;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleDataModelConverter implements Converter<VehicleDataList, List<Vehicle>> {

    @Override
    public List<Vehicle> convert(VehicleDataList data) {
        List<String> plateNumberList = data.getVehicles().stream()
                .map(VehicleDataList.VehicleData::getPlateNumber).collect(Collectors.toList());
        return plateNumberList.stream().map(Vehicle::new).collect(Collectors.toList());
    }
}
