package com.fleetmanagement.controller;

import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.service.RouteService;
import com.fleetmanagement.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Locale;

@Validated
@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {

    private Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createVehicle(@Valid @RequestBody VehicleDataList vehicleDataList) {
        logger.info("--creating vehicle--");
        vehicleService.saveVehicles(vehicleDataList);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("vehicle.created",null, Locale.ENGLISH));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleDataList> getAllVehicles() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getAllVehicles());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unload/{plateNumber}")
    public ResponseEntity<RoutePlanData> unloadDeliveriesForVehicle(@PathVariable @Pattern(regexp = "[0-8][0-9][a-zA-Z]{1,3}[0-9]{2,5}",message = "{vehicle.plate.number.incorrect}") String plateNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(routeService.unloadDeliveries(plateNumber));
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
