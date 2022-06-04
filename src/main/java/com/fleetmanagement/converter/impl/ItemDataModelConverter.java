package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.item.ItemDataList;
import com.fleetmanagement.model.item.Bag;
import com.fleetmanagement.model.item.Item;
import com.fleetmanagement.model.item.Package;
import com.fleetmanagement.service.DeliveryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("itemDataModelConverter")
public class ItemDataModelConverter implements Converter<ItemDataList, List<Item>> {

    @Autowired
    private DeliveryPointService deliveryPointService;

    /*Bag and Package items can be differentiated by their volume value */
    @Override
    public List<Item> convert(ItemDataList itemDataList) {
        List<Item> allItems = new LinkedList<>();
        List<Bag> bagList = getBags(itemDataList);
        List<Package> packageList = getPackages(itemDataList);
        allItems.addAll(bagList);
        allItems.addAll(packageList);
        return allItems;
    }

    private List<Package> getPackages(ItemDataList itemDataList) {
        List<Package> packageList = new LinkedList<>();
        List<ItemDataList.ItemData> packageItems = itemDataList.getItems().stream().
                filter(itemData -> itemData.getVolumetricWeight() > 0).
                collect(Collectors.toList());

        packageItems.forEach(pi -> {
            Package aPackage = new Package();
            aPackage.setBarcode(pi.getBarcode());
            aPackage.setDeliveryPoint(deliveryPointService.getDeliveryPointById(pi.getDeliveryPoint()));
            aPackage.setVolumetricWeight(pi.getVolumetricWeight());
            packageList.add(aPackage);
        });
        return packageList;
    }

    private List<Bag> getBags(ItemDataList itemDataList) {
        List<Bag> bagList = new LinkedList<>();
        List<ItemDataList.ItemData> bagItems = itemDataList.getItems().stream().
                filter(itemData -> itemData.getVolumetricWeight()==0).
                collect(Collectors.toList());

        bagItems.forEach(bi -> {
            Bag bag = new Bag();
            bag.setBarcode(bi.getBarcode());
            bag.setDeliveryPoint(deliveryPointService.getDeliveryPointById(bi.getDeliveryPoint()));
            bagList.add(bag);
        });
        return bagList;
    }

    public void setDeliveryPointService(DeliveryPointService deliveryPointService) {
        this.deliveryPointService = deliveryPointService;
    }
}
