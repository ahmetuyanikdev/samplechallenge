package com.fleetmanagement.service;

import com.fleetmanagement.data.item.ItemAssignmentDataList;
import com.fleetmanagement.data.item.ItemDataList;
import com.fleetmanagement.model.item.Item;

import java.util.List;

public interface ItemService {
    List<Item> saveItems(ItemDataList itemDataList);
    ItemDataList getAllItems();
    Item getItemByBarcode(String barcode);
    List<Item> assignItems(ItemAssignmentDataList assignmentDataList);
}
