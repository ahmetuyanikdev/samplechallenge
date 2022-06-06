package com.fleetmanagement.controller;

import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {

    private Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createVehicle(@RequestBody VehicleDataList vehicleDataList){
        logger.info(vehicleDataList.toString());
        vehicleService.saveVehicles(vehicleDataList);
        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleDataList getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
