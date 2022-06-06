package com.fleetmanagement.converter.impl;

import com.fleetmanagement.constant.BagStatus;
import com.fleetmanagement.constant.PackageStatus;
import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.repository.DeliveryPointRepository;
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
    private DeliveryPointRepository deliveryPointRepository;

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
            Package aPackage = Package.getInstance();
            populatePackage(pi, aPackage);
            packageList.add(aPackage);
        });
        return packageList;
    }

    private void populatePackage(ShipmentDataList.ShipmentData pi, Package aPackage) {
        aPackage.setBarcode(pi.getBarcode());
        aPackage.setStatus(PackageStatus.Created.getValue());
        aPackage.setDeliveryPoint(deliveryPointRepository.findById(pi.getDeliveryPoint()));
        aPackage.setVolumetricWeight(pi.getVolumetricWeight());
    }

    private List<Bag> getBags(ShipmentDataList shipmentDataList) {
        List<Bag> bagList = new LinkedList<>();
        List<ShipmentDataList.ShipmentData> bagItems = shipmentDataList.getShipments().stream().
                filter(shipmentData -> shipmentData.getVolumetricWeight()==0).
                collect(Collectors.toList());

        bagItems.forEach(bi -> {
            Bag bag = Bag.getInstance();
            populateBag(bi, bag);
            bagList.add(bag);
        });
        return bagList;
    }

    private void populateBag(ShipmentDataList.ShipmentData bi, Bag bag) {
        bag.setBarcode(bi.getBarcode());
        bag.setStatus(BagStatus.Created.getValue());
        bag.setDeliveryPoint(deliveryPointRepository.findById(bi.getDeliveryPoint()));
    }

    public void setDeliveryPointRepository(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }
}
