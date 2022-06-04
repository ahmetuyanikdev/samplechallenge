package com.fleetmanagement.controller;

import com.fleetmanagement.data.item.ItemAssignmentDataList;
import com.fleetmanagement.data.item.ItemDataList;
import com.fleetmanagement.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpStatus createItem(@RequestBody ItemDataList itemDataList) {
        logger.info(itemDataList.toString());
        itemService.saveItems(itemDataList);
        return HttpStatus.OK;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ItemDataList getAllItems(){
        return itemService.getAllItems();
    }

    @RequestMapping(value = "/assignments",method = RequestMethod.POST)
    public HttpStatus assignPackagesToBags(@RequestBody ItemAssignmentDataList itemAssignmentDataList) {
        logger.info(itemAssignmentDataList.toString());
        return HttpStatus.OK;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
