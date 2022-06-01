package com.fleetmanagement.service.impl;

import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.populator.Converter;
import com.fleetmanagement.populator.ReverseConverter;
import com.fleetmanagement.repository.VehicleRepository;
import com.fleetmanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    @Qualifier("vehicleDataModelConverter")
    private Converter<VehicleDataList,List<Vehicle>> converter;

    @Autowired
    @Qualifier("vehicleModelDataConverter")
    private ReverseConverter<List<Vehicle>,VehicleDataList> reverseConverter;

    @Override
    public void saveVehicles(VehicleDataList vehicleDataList) {
        List<Vehicle> vehicles = converter.convert(vehicleDataList);
        vehicleRepository.saveAll(vehicles);
    }

    @Override
    public VehicleDataList getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return reverseConverter.convert(vehicles);
    }

    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void setConverter(Converter<VehicleDataList, List<Vehicle>> converter) {
        this.converter = converter;
    }

    public void setReverseConverter(ReverseConverter<List<Vehicle>, VehicleDataList> reverseConverter) {
        this.reverseConverter = reverseConverter;
    }

}
