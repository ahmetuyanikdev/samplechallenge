package com.fleetmanagement.controller;

import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/vehicle")
public class VehicleController {

    private Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus createVehicle(@RequestBody VehicleDataList vehicleDataList){
        logger.info(vehicleDataList.toString());
        vehicleService.save(vehicleDataList);
        return HttpStatus.OK;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
