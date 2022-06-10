package com.fleetmanagement.controller;

import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.IncorrectDeliveryDataList;
import com.fleetmanagement.service.DeliveryPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/delivery-points")
public class DeliveryPointController {

    private Logger logger = LoggerFactory.getLogger(DeliveryPointController.class);

    @Autowired
    private DeliveryPointService deliveryPointService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createDeliveryPoint(@Valid @RequestBody DeliveryPointDataList deliveryPointDataList){
       logger.info(deliveryPointDataList.toString());
       deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
       return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET)
    public DeliveryPointDataList getAllDeliveryPoints(){
        return deliveryPointService.getAllDeliveryPoints();
    }

    @RequestMapping(value = "/incorrectShipments/{deliveryPointId}",method = RequestMethod.GET)
    public IncorrectDeliveryDataList getIncorrectDeliveriesForDeliveryPoint(@PathVariable int deliveryPointId){
        return deliveryPointService.getAllIncorrectlyDeliveriesForDeliveryPoint(deliveryPointId);
    }

    public void setDeliveryPointService(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

}
