package com.fleetmanagement.test.service;


import com.fleetmanagement.FleetManagementApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = FleetManagementApplication.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public abstract class ServiceIntegrationTest {

}
