package com.fleetmanagement.controller;

import com.fleetmanagement.data.item.ShipmentAssignmentDataList;
import com.fleetmanagement.data.item.ShipmentDataList;
import com.fleetmanagement.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/shipments")
public class ShipmentController {

    private Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createShipment(@RequestBody ShipmentDataList shipmentDataList) {
        logger.info(shipmentDataList.toString());
        shipmentService.saveShipments(shipmentDataList);
        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ShipmentDataList getAllShipments(){
        return shipmentService.getAllShipments();
    }

    @RequestMapping(value = "/assignments",method = RequestMethod.POST)
    public ShipmentDataList assignPackagesToBags(@RequestBody ShipmentAssignmentDataList shipmentAssignmentDataList) {
        return shipmentService.assignShipments(shipmentAssignmentDataList);
    }

    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }
}
