package com.fleetmanagement.controller;

import com.fleetmanagement.data.item.ItemAssignmentDataList;
import com.fleetmanagement.data.item.ItemDataList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createItem(@RequestBody ItemDataList itemDataList) {
        logger.info(itemDataList.toString());
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/assignments",method = RequestMethod.POST)
    public HttpStatus assignPackagesToBags(@RequestBody ItemAssignmentDataList itemAssignmentDataList) {
        logger.info(itemAssignmentDataList.toString());
        return HttpStatus.OK;
    }
}
