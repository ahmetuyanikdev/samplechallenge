package com.fleetmanagement.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fleetmanagement.FleetManagementApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public abstract class ControllerTest {

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

    public String getStringPayload(Object data) throws JsonProcessingException {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return writer.writeValueAsString(data);
    }

}
