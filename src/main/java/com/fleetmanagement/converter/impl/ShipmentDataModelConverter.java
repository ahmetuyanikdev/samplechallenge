package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.item.ShipmentDataList;
import com.fleetmanagement.model.item.Bag;
import com.fleetmanagement.model.item.Shipment;
import com.fleetmanagement.model.item.Package;
import com.fleetmanagement.service.DeliveryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("shipmentDataModelConverter")
public class ShipmentDataModelConverter implements Converter<ShipmentDataList, List<Shipment>> {

    @Autowired
    private DeliveryPointService deliveryPointService;

    /*Bag and Package items can be differentiated by their volume value */
    @Override
    public List<Shipment> convert(ShipmentDataList shipmentDataList) {
        List<Shipment> allShipments = new LinkedList<>();
        List<Bag> bagList = getBags(shipmentDataList);
        List<Package> packageList = getPackages(shipmentDataList);
        allShipments.addAll(bagList);
        allShipments.addAll(packageList);
        return allShipments;
    }

    private List<Package> getPackages(ShipmentDataList shipmentDataList) {
        List<Package> packageList = new LinkedList<>();
        List<ShipmentDataList.ShipmentData> packageItems = shipmentDataList.getShipments().stream().
                filter(shipmentData -> shipmentData.getVolumetricWeight() > 0).
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

    private List<Bag> getBags(ShipmentDataList shipmentDataList) {
        List<Bag> bagList = new LinkedList<>();
        List<ShipmentDataList.ShipmentData> bagItems = shipmentDataList.getShipments().stream().
                filter(shipmentData -> shipmentData.getVolumetricWeight()==0).
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
