package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.item.ItemAssignmentDataList;
import com.fleetmanagement.model.item.Bag;
import com.fleetmanagement.model.item.Item;
import com.fleetmanagement.model.item.Package;
import com.fleetmanagement.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Qualifier("itemAssignmentDataListModelConverter")
public class ItemAssignmentDataListModelConverter implements Converter<ItemAssignmentDataList, List<Item>> {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> convert(ItemAssignmentDataList assignmentDataList) {

        Set<String> bagBarcodes = assignmentDataList.getItemAssignments().
                stream().map(ItemAssignmentDataList.ItemAssignment::getBagBarcode).collect(Collectors.toSet());

        Set<String> packBarcodes = assignmentDataList.getItemAssignments().
                stream().map(ItemAssignmentDataList.ItemAssignment::getBarcode).collect(Collectors.toSet());

        Map<String, Package> packageMap = getPackagesMap(packBarcodes);
        Map<String, Bag> bagMap = getBagsMap(bagBarcodes);

        List<Item> allAssignedItems = getAssignedItems(assignmentDataList, packageMap, bagMap);
        return allAssignedItems;
    }

    private List<Item> getAssignedItems(ItemAssignmentDataList assignmentDataList, Map<String, Package> packageMap, Map<String, Bag> bagMap) {
        assignmentDataList.getItemAssignments().forEach(itemAssignment -> {
            packageMap.get(itemAssignment.getBarcode()).setBag(bagMap.get(itemAssignment.getBagBarcode()));
            bagMap.get(itemAssignment.getBagBarcode()).addItem(packageMap.get(itemAssignment.getBarcode()));
        });

        List<Item> allAssignedItems = new LinkedList<>();
        allAssignedItems.addAll(packageMap.values());
        allAssignedItems.addAll(bagMap.values());
        return allAssignedItems;
    }

    private Map<String,Package> getPackagesMap(Set<String> packBarcodes) {
        return itemRepository.findAllById(packBarcodes).stream().filter(item -> item instanceof Package)
                .map(item -> (Package) item).
                        collect(Collectors.toMap(Package::getBarcode, Function.identity()));
    }

    private Map<String,Bag> getBagsMap(Set<String> bagBarcodes) {
        return itemRepository.findAllById(bagBarcodes).stream().filter(item -> item instanceof Bag)
                .map(item -> (Bag) item).
                        collect(Collectors.toMap(Bag::getBarcode, Function.identity()));
    }

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
}
