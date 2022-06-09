package com.fleetmanagement.test.controller;


import com.fleetmanagement.controller.VehicleController;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.LinkedList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VehicleControllerTest extends ControllerTest{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleController controller;

    @Mock
    private VehicleService vehicleService;

    private VehicleDataList vehicleDataList;

    private String vehiclePostPayload;

    @Before
    public void setup() throws Exception {
        controller.setVehicleService(vehicleService);
        vehicleDataList = new VehicleDataList();
        List<VehicleDataList.VehicleData> vehicles = new LinkedList<>();
        VehicleDataList.VehicleData vehicle = new VehicleDataList.VehicleData();
        vehicle.setPlateNumber("34TL34");
        vehicles.add(vehicle);
        vehicleDataList.setVehicles(vehicles);
        vehiclePostPayload = super.getStringPayload(vehicleDataList);
    }

    @Test
    public void test_saveVehicles_success() throws Exception {
        mvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON).
                content(vehiclePostPayload).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void test_saveVehicle_WrongBody_fail() throws Exception {
        vehicleDataList.getVehicles().get(0).setPlateNumber("asdwqw");
        vehiclePostPayload = super.getStringPayload(vehicleDataList);
        mvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON).
                content(vehiclePostPayload).characterEncoding("utf-8")).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    public void test_getVehicle_success() throws Exception {
        Mockito.when(vehicleService.getAllVehicles()).thenReturn(vehicleDataList);
        mvc.perform(get("/vehicles").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andExpect(content().
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("vehicles[0].plateNumber").value("34TL34"));

    }
}
