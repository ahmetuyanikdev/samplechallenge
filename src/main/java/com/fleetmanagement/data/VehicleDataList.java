package com.fleetmanagement.data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class VehicleDataList {

    private List<@Valid VehicleData> vehicles;

    public VehicleDataList(){

    }

    public static class VehicleData{

        public VehicleData(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public VehicleData() {

        }
        @NotNull(message = "Plate number missing")
        @Pattern(regexp = "[0-8][0-9][a-zA-Z]{1,3}[0-9]{2,5}",message = "Incorrect plate number")
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
