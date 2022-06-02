package unit.controller;

import com.fleetmanagement.controller.VehicleController;
import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.service.VehicleService;
import org.junit.Before;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

public class VehicleControllerUnitTest extends AbstractControllerTest {

    VehicleService vehicleService;

    VehicleDataList vehicleDataList;

    List<VehicleDataList.VehicleData> vehicles;

    @Before
    @Override
    public void setup(){
        super.setup();
        vehicleDataList = new VehicleDataList();
        VehicleDataList.VehicleData vehicleData = new VehicleDataList.VehicleData();
        vehicleData.setPlateNumber("34ABC001");
        vehicles = new LinkedList<>();
        vehicles.add(vehicleData);
        vehicleDataList.setVehicles(vehicles);
    }


    public void test_getAllVehicles() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(vehicleDataList);
        //VehicleDataList vehicleDataList = vehicleController.getAllVehicles();
        assertEquals(vehicleDataList.getVehicles().isEmpty(),false);

    }

}
