package com.fleetmanagement.controller;

import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.service.DeliveryPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/delivery-points")
public class DeliveryPointController {

    private Logger logger = LoggerFactory.getLogger(DeliveryPointController.class);

    @Autowired
    private DeliveryPointService deliveryPointService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createDeliveryPoint(@RequestBody DeliveryPointDataList deliveryPointDataList){
       logger.info(deliveryPointDataList.toString());
       deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
       return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
    public DeliveryPointDataList getAllDeliveryPoints(){
        return deliveryPointService.getAllDeliveryPoints();
    }

    public void setDeliveryPointService(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

}
