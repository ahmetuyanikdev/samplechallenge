package com.fleetmanagement.converter.impl;

import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.converter.ReverseConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("vehicleModelDataConverter")
public class VehicleModelDataConverter implements ReverseConverter<List<Vehicle>, VehicleDataList> {

    @Override
    public VehicleDataList convert(List<Vehicle> vehicles) {
        VehicleDataList vehicleDataList = new VehicleDataList();
        List<String> plateNumbers = vehicles.stream().
                map(Vehicle::getPlateNumber).collect(Collectors.toList());
        List<VehicleDataList.VehicleData> vehicleList = plateNumbers.stream().map(VehicleDataList.VehicleData::new)
                .collect(Collectors.toList());
        vehicleDataList.setVehicles(vehicleList);
        return vehicleDataList;
    }
}

