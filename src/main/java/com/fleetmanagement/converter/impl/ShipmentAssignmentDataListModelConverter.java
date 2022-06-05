package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.data.item.ShipmentAssignmentDataList;
import com.fleetmanagement.model.item.Bag;
import com.fleetmanagement.model.item.Shipment;
import com.fleetmanagement.model.item.Package;
import com.fleetmanagement.repository.ShipmentRepository;
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
@Qualifier("shipmentAssignmentDataListModelConverter")
public class ShipmentAssignmentDataListModelConverter implements Converter<ShipmentAssignmentDataList, List<Shipment>> {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public List<Shipment> convert(ShipmentAssignmentDataList assignmentDataList) {

        Set<String> bagBarcodes = assignmentDataList.getShipmentAssignments().
                stream().map(ShipmentAssignmentDataList.ShipmentAssignment::getBagBarcode).collect(Collectors.toSet());

        Set<String> packBarcodes = assignmentDataList.getShipmentAssignments().
                stream().map(ShipmentAssignmentDataList.ShipmentAssignment::getBarcode).collect(Collectors.toSet());

        Map<String, Package> packageMap = getPackagesMap(packBarcodes);
        Map<String, Bag> bagMap = getBagsMap(bagBarcodes);

        List<Shipment> allAssignedShipments = getAssignedItems(assignmentDataList, packageMap, bagMap);
        return allAssignedShipments;
    }

    private List<Shipment> getAssignedItems(ShipmentAssignmentDataList assignmentDataList, Map<String, Package> packageMap, Map<String, Bag> bagMap) {
        assignmentDataList.getShipmentAssignments().forEach(shipmentAssignment -> {
            packageMap.get(shipmentAssignment.getBarcode()).setBag(bagMap.get(shipmentAssignment.getBagBarcode()));
            bagMap.get(shipmentAssignment.getBagBarcode()).addItem(packageMap.get(shipmentAssignment.getBarcode()));
        });

        List<Shipment> allAssignedShipments = new LinkedList<>();
        allAssignedShipments.addAll(packageMap.values());
        allAssignedShipments.addAll(bagMap.values());
        return allAssignedShipments;
    }

    private Map<String,Package> getPackagesMap(Set<String> packBarcodes) {
        return shipmentRepository.findAllById(packBarcodes).stream().filter(item -> item instanceof Package)
                .map(item -> (Package) item).
                        collect(Collectors.toMap(Package::getBarcode, Function.identity()));
    }

    private Map<String,Bag> getBagsMap(Set<String> bagBarcodes) {
        return shipmentRepository.findAllById(bagBarcodes).stream().filter(item -> item instanceof Bag)
                .map(item -> (Bag) item).
                        collect(Collectors.toMap(Bag::getBarcode, Function.identity()));
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }
}
