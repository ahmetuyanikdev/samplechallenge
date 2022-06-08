package com.fleetmanagement.controller;

import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.service.RouteService;
import com.fleetmanagement.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {

    private Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RouteService routeService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createVehicle(@Valid @RequestBody VehicleDataList vehicleDataList){
        logger.info(vehicleDataList.toString());
        vehicleService.saveVehicles(vehicleDataList);
        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleDataList getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @RequestMapping(method = RequestMethod.GET,value = "/unload/{plateNumber}")
    public RoutePlanData unloadDeliveriesForVehicle(@PathVariable String plateNumber){
        return routeService.unloadDeliveries(plateNumber);
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
