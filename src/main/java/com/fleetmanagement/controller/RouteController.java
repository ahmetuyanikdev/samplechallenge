package com.fleetmanagement.controller;


import com.fleetmanagement.data.vehicle.VehiclePlanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/routes")
public class RouteController {

    private Logger logger = LoggerFactory.getLogger(RouteController.class);

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createRoutes(@RequestBody VehiclePlanData vehiclePlanData){
        logger.info(vehiclePlanData.toString());
        return HttpStatus.OK;
    }
}
