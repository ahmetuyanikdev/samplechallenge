package com.fleetmanagement.repository;

import com.fleetmanagement.model.shipment.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment,String> {
    List<Shipment> getShipmentsByDeliveryPoint(int id);
    Shipment getShipmentByBarcode(String barcode);
}
