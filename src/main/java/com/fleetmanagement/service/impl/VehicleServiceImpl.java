package com.fleetmanagement.service.impl;

import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.populator.impl.VehicleDataModelConverter;
import com.fleetmanagement.repository.VehicleRepository;
import com.fleetmanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleDataModelConverter converter;

    @Override
    public void save(VehicleDataList vehicleDataList) {
        List<Vehicle> vehicles = converter.convert(vehicleDataList);
        vehicleRepository.saveAll(vehicles);
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void setConverter(VehicleDataModelConverter converter) {
        this.converter = converter;
    }
}
