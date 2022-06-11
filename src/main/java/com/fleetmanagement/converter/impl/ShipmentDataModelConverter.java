package com.fleetmanagement.converter.impl;

import com.fleetmanagement.constant.BagStatus;
import com.fleetmanagement.constant.PackageStatus;
import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.exception.NoDataFoundException;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.repository.DeliveryPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("shipmentDataModelConverter")
public class ShipmentDataModelConverter implements Converter<ShipmentDataList, List<Shipment>> {

    @Autowired
    private DeliveryPointRepository deliveryPointRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean violation(ShipmentDataList shipmentDataList) {
        return shipmentDataList.getShipments().stream().anyMatch(shipmentData ->
                Objects.equals(shipmentData.getDeliveryPoint(), 0) || Objects.equals(shipmentData.getBarcode(), ""));
    }

    /*Bag and Package items can be differentiated by their volume value */
    @Override
    public List<Shipment> convert(ShipmentDataList shipmentDataList) {
        if (violation(shipmentDataList)) {
            throw new DataConversionException(messageSource.getMessage("shipment.data.violation", null, Locale.ENGLISH));
        }
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
                filter(ShipmentDataList.ShipmentData::isPackage).
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
        aPackage.setDeliveryPoint(deliveryPointRepository.findById(pi.getDeliveryPoint()).get());
        aPackage.setVolumetricWeight(pi.getVolumetricWeight());
    }

    private List<Bag> getBags(ShipmentDataList shipmentDataList) {
        List<Bag> bagList = new LinkedList<>();
        List<ShipmentDataList.ShipmentData> bagItems = shipmentDataList.getShipments().stream().
                filter(shipmentData -> !shipmentData.isPackage()).
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
        DeliveryPoint deliveryPoint = deliveryPointRepository.findById(bi.getDeliveryPoint()).
                orElseThrow(() -> new NoDataFoundException(messageSource.getMessage("shipment.delivery-point.not-found", null, Locale.ENGLISH)));
        bag.setDeliveryPoint(deliveryPoint);
    }

    public void setDeliveryPointRepository(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }
}
