package com.fleetmanagement.service;

import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.model.Vehicle;

import java.util.List;

public interface VehicleService {
     List<Vehicle> saveVehicles(VehicleDataList vehicleDataList);
     VehicleDataList getAllVehicles();
}
