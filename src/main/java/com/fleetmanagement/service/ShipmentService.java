package com.fleetmanagement.service;

import com.fleetmanagement.data.item.ShipmentAssignmentDataList;
import com.fleetmanagement.data.item.ShipmentDataList;
import com.fleetmanagement.model.item.Shipment;

import java.util.List;

public interface ShipmentService {
    List<Shipment> saveShipments(ShipmentDataList shipmentDataList);
    ShipmentDataList getAllShipments();
    Shipment getShipmentByBarcode(String barcode);
    List<Shipment> assignShipments(ShipmentAssignmentDataList assignmentDataList);
}
