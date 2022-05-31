package com.fleetmanagement.controller;

import com.fleetmanagement.data.DeliveryPointDataList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/delivery-point")
public class DeliveryPointController {

    Logger logger = LoggerFactory.getLogger(DeliveryPointController.class);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus createDeliveryPoint(@RequestBody DeliveryPointDataList deliveryPointDataList){
       logger.info(deliveryPointDataList.toString());
        return HttpStatus.OK;
    }
}
