package com.fleetmanagement.service;

import com.fleetmanagement.data.shipment.ShipmentAssignmentDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.shipment.Shipment;

import java.util.List;
import java.util.Set;

public interface ShipmentService {
    List<Shipment> saveShipments(ShipmentDataList shipmentDataList);
    ShipmentDataList getAllShipments();
    ShipmentDataList assignShipments(ShipmentAssignmentDataList assignmentDataList);
}
