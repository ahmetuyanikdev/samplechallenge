package com.fleetmanagement.data.vehicle;

import java.util.List;

public class VehicleDataList {

    private List<VehicleData> vehicles;

    public VehicleDataList(){

    }

    public static class VehicleData{

        public VehicleData(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public VehicleData() {

        }

        private String plateNumber;

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }
    }

    public List<VehicleData> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleData> vehicles) {
        this.vehicles = vehicles;
    }
}
