package com.fleetmanagement.controller;

import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.IncorrectDeliveryDataList;
import com.fleetmanagement.service.DeliveryPointService;
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
@RequestMapping(value = "/delivery-points")
public class DeliveryPointController {

    private Logger logger = LoggerFactory.getLogger(DeliveryPointController.class);

    @Autowired
    private DeliveryPointService deliveryPointService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDeliveryPoint(@Valid @RequestBody DeliveryPointDataList deliveryPointDataList){
       logger.info("--creating delivery point--");
       deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
       return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("delivery-point.created",null, Locale.ENGLISH));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<DeliveryPointDataList> getAllDeliveryPoints(){
        logger.info("--getting delivery point--");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPointService.getAllDeliveryPoints());
    }

    @RequestMapping(value = "/incorrectShipments/{deliveryPointId}",method = RequestMethod.GET)
    public ResponseEntity<IncorrectDeliveryDataList> getIncorrectDeliveriesForDeliveryPoint(@PathVariable int deliveryPointId){
        logger.info("--getting incorrect shipments--");
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPointService.getAllIncorrectlyDeliveriesForDeliveryPoint(deliveryPointId));
    }

    public void setDeliveryPointService(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }

}
