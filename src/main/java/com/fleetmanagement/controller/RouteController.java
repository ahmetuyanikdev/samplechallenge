package com.fleetmanagement.controller;


import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/routes")
public class RouteController {

    private Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createRoutes(@RequestBody RoutePlanData routePlanData){
        logger.info(routePlanData.toString());
        routeService.saveRoutes(routePlanData);
        return HttpStatus.OK;
    }

    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }
}
