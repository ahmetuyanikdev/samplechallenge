package com.fleetmanagement.service.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.converter.impl.ShipmentDataModelConverter;
import com.fleetmanagement.data.item.ShipmentAssignmentDataList;
import com.fleetmanagement.data.item.ShipmentDataList;
import com.fleetmanagement.model.item.Shipment;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    @Qualifier("shipmentDataModelConverter")
    private Converter<ShipmentDataList, List<Shipment>> converter;

    @Autowired
    @Qualifier("shipmentModelDataConverter")
    private ReverseConverter<List<Shipment>, ShipmentDataList> reverseConverter;

    @Autowired
    @Qualifier("shipmentAssignmentDataListModelConverter")
    private Converter<ShipmentAssignmentDataList, List<Shipment>> itemAssignmentConverter;


    @Override
    public List<Shipment> saveShipments(ShipmentDataList shipmentDataList) {
        List<Shipment> shipments = converter.convert(shipmentDataList);
        return shipmentRepository.saveAll(shipments);
    }

    @Override
    public ShipmentDataList getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();
        return reverseConverter.convert(shipments);
    }

    @Override
    public Shipment getShipmentByBarcode(String barcode) {
        return shipmentRepository.getShipmentByBarcode(barcode);
    }

    @Override
    public List<Shipment> assignShipments(ShipmentAssignmentDataList assignmentDataList) {
        List<Shipment> allAssignedShipments = itemAssignmentConverter.convert(assignmentDataList);
        return shipmentRepository.saveAll(allAssignedShipments);
    }

    public void setReverseConverter(ReverseConverter<List<Shipment>, ShipmentDataList> reverseConverter) {
        this.reverseConverter = reverseConverter;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setConverter(ShipmentDataModelConverter converter) {
        this.converter = converter;
    }

    public void setItemAssignmentConverter(Converter<ShipmentAssignmentDataList, List<Shipment>> itemAssignmentConverter) {
        this.itemAssignmentConverter = itemAssignmentConverter;
    }
}
