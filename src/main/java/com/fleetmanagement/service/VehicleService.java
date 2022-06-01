package com.fleetmanagement.service;

import com.fleetmanagement.data.vehicle.VehicleDataList;

public interface VehicleService {
     void saveVehicles(VehicleDataList vehicleDataList);
     VehicleDataList getAllVehicles();
}
