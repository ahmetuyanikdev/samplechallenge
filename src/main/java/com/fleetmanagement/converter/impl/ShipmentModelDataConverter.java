package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.item.ShipmentDataList;
import com.fleetmanagement.model.shipment.Bag;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.model.shipment.Package;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("shipmentModelDataConverter")
public class ShipmentModelDataConverter implements ReverseConverter<List<Shipment>, ShipmentDataList> {

    private Map<Class, InternalConverter> converterMap;

    @PostConstruct
    public void setInternalConverter() {
        converterMap = new HashMap<>();
        converterMap.put(Bag.class, new BagInternalConverter());
        converterMap.put(Package.class, new PackageInternalConverter());
    }

    @Override
    public ShipmentDataList convert(List<Shipment> shipments) {
        List<ShipmentDataList.ShipmentData> dataList = shipments.stream()
                .map(i -> converterMap.get(i.getClass()).
                        convert(i)).collect(Collectors.toList());
        ShipmentDataList shipmentDataList = new ShipmentDataList();
        shipmentDataList.setShipments(dataList);
        return shipmentDataList;
    }

    public class InternalConverter {
        protected ShipmentDataList.ShipmentData convert(Shipment shipment) {
            ShipmentDataList.ShipmentData shipmentData = new ShipmentDataList.ShipmentData();
            shipmentData.setBarcode(shipment.getBarcode());
            shipmentData.setDeliveryPoint(shipment.getDeliveryPoint().getId());
            shipmentData.setStatus(shipment.getStatus());
            return shipmentData;
        }
    }

    public class BagInternalConverter extends InternalConverter {
        @Override
        protected ShipmentDataList.ShipmentData convert(Shipment shipment) {
            return super.convert(shipment);
        }
    }

    public class PackageInternalConverter extends InternalConverter {
        @Override
        protected ShipmentDataList.ShipmentData convert(Shipment shipment) {
            ShipmentDataList.ShipmentData shipmentData = super.convert(shipment);
            shipmentData.setVolumetricWeight(((Package) shipment).getVolumetricWeight());
            return shipmentData;
        }
    }

}
