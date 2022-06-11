package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.shipment.ShipmentAssignmentDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.exception.NoDataFoundException;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.model.shipment.Package;
import com.fleetmanagement.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Qualifier("shipmentAssignmentDataListModelConverter")
public class ShipmentAssignmentDataListModelConverter implements Converter<ShipmentAssignmentDataList, List<Shipment>> {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean violation(ShipmentAssignmentDataList shipmentAssignmentDataList) {
        return shipmentAssignmentDataList.getShipmentAssignments().stream().anyMatch(shipmentAssignment ->
                shipmentAssignment.getBarcode().equals("") || shipmentAssignment.getBagBarcode().equals(""));
    }

    @Override
    public List<Shipment> convert(ShipmentAssignmentDataList assignmentDataList) {
        if (violation(assignmentDataList)) {
            throw new DataConversionException(messageSource.getMessage("shipment.data.violation", null, Locale.ENGLISH));
        }

        Set<String> bagBarcodes = assignmentDataList.getShipmentAssignments().
                stream().map(ShipmentAssignmentDataList.ShipmentAssignment::getBagBarcode).collect(Collectors.toSet());

        Set<String> packBarcodes = assignmentDataList.getShipmentAssignments().
                stream().map(ShipmentAssignmentDataList.ShipmentAssignment::getBarcode).collect(Collectors.toSet());

        Map<String, Package> packageMap = getPackagesMap(packBarcodes);
        Map<String, Bag> bagMap = getBagsMap(bagBarcodes);
        checkIfAllShipmentsExist(bagBarcodes, packBarcodes, packageMap, bagMap);
        return getAssignedItems(assignmentDataList, packageMap, bagMap);
    }

    private void checkIfAllShipmentsExist(Set<String> bagBarcodes, Set<String> packBarcodes, Map<String, Package> packageMap, Map<String, Bag> bagMap) {
        Set<String> existingShipments = new HashSet<>(bagMap.values().stream().map(Bag::getBarcode).collect(Collectors.toSet()));
        existingShipments.addAll(packageMap.values().stream().map(Package::getBarcode).collect(Collectors.toSet()));
        if (!(existingShipments.containsAll(bagBarcodes) && existingShipments.containsAll(packBarcodes))) {
            throw new NoDataFoundException(messageSource.getMessage("shipment.assignment.not-found", null, Locale.ENGLISH));
        }
    }

    private List<Shipment> getAssignedItems(ShipmentAssignmentDataList assignmentDataList, Map<String, Package> packageMap, Map<String, Bag> bagMap) {
        assignmentDataList.getShipmentAssignments().forEach(shipmentAssignment -> {
            Package pack = packageMap.get(shipmentAssignment.getBarcode());
            pack.setBag(bagMap.get(shipmentAssignment.getBagBarcode()));
            packageMap.put(shipmentAssignment.getBarcode(), pack);

            Bag bag = bagMap.get(shipmentAssignment.getBagBarcode());
            bag.addPackage(packageMap.get(shipmentAssignment.getBarcode()));
            bagMap.put(shipmentAssignment.getBarcode(), bag);
        });

        List<Shipment> allAssignedShipments = new LinkedList<>();
        allAssignedShipments.addAll(packageMap.values());
        allAssignedShipments.addAll(bagMap.values());
        return allAssignedShipments;
    }

    private Map<String, Package> getPackagesMap(Set<String> packBarcodes) {
        return shipmentRepository.findAllById(packBarcodes).stream().filter(item -> item instanceof Package)
                .map(item -> (Package) item).
                        collect(Collectors.toMap(Package::getBarcode, Function.identity()));
    }

    private Map<String, Bag> getBagsMap(Set<String> bagBarcodes) {
        return shipmentRepository.findAllById(bagBarcodes).stream().filter(item -> item instanceof Bag)
                .map(item -> (Bag) item).
                        collect(Collectors.toMap(Bag::getBarcode, Function.identity()));
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }
}
