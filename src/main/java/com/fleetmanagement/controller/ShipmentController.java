package com.fleetmanagement.controller;

import com.fleetmanagement.data.shipment.ShipmentAssignmentDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.service.ShipmentService;
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
@RequestMapping(value = "/shipments")
public class ShipmentController {

    private Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createShipment(@Valid @RequestBody ShipmentDataList shipmentDataList) {
        logger.info("--creating shipment(s)--");
        shipmentService.saveShipments(shipmentDataList);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("shipment.created", null, Locale.ENGLISH));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ShipmentDataList> getAllShipments() {
        logger.info("--getting all shipment(s)--");
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.getAllShipments());
    }

    @RequestMapping(value = "/assignments", method = RequestMethod.PATCH)
    public ResponseEntity<ShipmentDataList> assignPackagesToBags(@Valid @RequestBody ShipmentAssignmentDataList shipmentAssignmentDataList) {
        logger.info("--assigning shipment(s)--");
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.assignShipments(shipmentAssignmentDataList));
    }

    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }
}
