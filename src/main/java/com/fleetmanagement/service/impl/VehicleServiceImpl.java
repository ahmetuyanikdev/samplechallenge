package com.fleetmanagement.service.impl;

import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
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
    public List<Vehicle> saveVehicles(VehicleDataList vehicleDataList) {
        List<Vehicle> vehicles = converter.convert(vehicleDataList);
        return vehicleRepository.saveAll(vehicles);
    }

    @Override
    public VehicleDataList getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return reverseConverter.convert(vehicles);
    }

    @Override
    public Vehicle getVehicleByPlateNumber(String plateNumber) {
        return vehicleRepository.getVehicleByPlateNumber(plateNumber);
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
