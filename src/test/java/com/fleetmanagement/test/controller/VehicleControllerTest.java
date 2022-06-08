package com.fleetmanagement.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fleetmanagement.FleetManagementApplication;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.repository.VehicleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = FleetManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class VehicleControllerTest {

    private VehicleDataList vehicleDataList;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    private ObjectMapper mapper;
    private ObjectWriter writer;
    private String vehiclePostPayload;
    @Before
    public void setup() throws Exception {
        vehicleDataList = new VehicleDataList();
        List<VehicleDataList.VehicleData> vehicles = new LinkedList<>();
        VehicleDataList.VehicleData vehicle = new VehicleDataList.VehicleData();
        vehicle.setPlateNumber("34TL34");
        vehicles.add(vehicle);
        vehicleDataList.setVehicles(vehicles);

        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        writer = mapper.writer().withDefaultPrettyPrinter();
        vehiclePostPayload = writer.writeValueAsString(vehicleDataList);
    }

    @Test
    public void test_saveVehicles_success() throws Exception {
        mvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON).
                content(vehiclePostPayload).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
    }
}
