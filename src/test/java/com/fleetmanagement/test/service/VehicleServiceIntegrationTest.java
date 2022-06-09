package com.fleetmanagement.test.service;


import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.service.impl.VehicleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VehicleServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private VehicleServiceImpl vehicleService;

    private VehicleDataList vehicleDataList;

    @Before
    public void setup() {
        vehicleDataList = new VehicleDataList();
        List<VehicleDataList.VehicleData> dataList = new LinkedList<>();
        VehicleDataList.VehicleData vehicleData1 = new VehicleDataList.VehicleData();
        vehicleData1.setPlateNumber("34TL34");
        dataList.add(vehicleData1);
        vehicleDataList.setVehicles(dataList);
    }

    @Test
    public void test_saveVehicles() {
        List<Vehicle> vehicles = vehicleService.saveVehicles(vehicleDataList);
        Assert.assertEquals("34TL34", vehicles.get(0).getPlateNumber());
    }

    @Test
    public void test_getVehiclesByPlateNumber() {
        Vehicle vehicle = vehicleService.getVehicleByPlateNumber("34TL34");
        Assert.assertTrue(Objects.nonNull(vehicle));
        Assert.assertEquals(vehicle.getPlateNumber(),"34TL34");
    }

}
