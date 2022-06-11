package com.fleetmanagement.controller;


import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping(value = "/routes")
public class RouteController {

    private Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createRoutes(@Valid @RequestBody RoutePlanData routePlanData) {
        logger.info("--creating routes--");
        routeService.saveRoutes(routePlanData);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("route.created", null, Locale.ENGLISH));
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }
}
