package com.fleetmanagement.service.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.IncorrectDeliveryDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.IncorrectDelivery;
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.repository.IncorrectDeliveryRepository;
import com.fleetmanagement.service.DeliveryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPointServiceImpl implements DeliveryPointService {

    @Autowired
    private DeliveryPointRepository deliveryPointRepository;

    @Autowired
    private IncorrectDeliveryRepository incorrectDeliveryRepository;

    @Autowired
    @Qualifier("deliveryPointDataModelConverter")
    private Converter<DeliveryPointDataList, List<DeliveryPoint>> converter;

    @Autowired
    @Qualifier("deliveryPointModelDataConverter")
    private ReverseConverter<List<DeliveryPoint>, DeliveryPointDataList> reverseConverter;

    @Autowired
    @Qualifier("incorrectDeliveryModelDataConverter")
    private Converter<List<IncorrectDelivery>, IncorrectDeliveryDataList> incorrectDeliveryDataListConverter;

    @Override
    public List<DeliveryPoint> saveDeliveryPoints(DeliveryPointDataList deliveryPointDataList) {
        List<DeliveryPoint> deliveryPoints = converter.convert(deliveryPointDataList);
        return deliveryPointRepository.saveAll(deliveryPoints);
    }

    @Override
    public DeliveryPointDataList getAllDeliveryPoints() {
        List<DeliveryPoint> deliveryPoints = deliveryPointRepository.findAll();
        return reverseConverter.convert(deliveryPoints);
    }

    @Override
    public IncorrectDeliveryDataList getAllIncorrectlyDeliveriesForDeliveryPoint(int deliveryPointID) {
        List<IncorrectDelivery> incorrectDeliveries = incorrectDeliveryRepository.findAllByDeliveryPointId(deliveryPointID);
        return incorrectDeliveryDataListConverter.convert(incorrectDeliveries);
    }

    public void setDeliveryPointRepository(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }

    public void setIncorrectDeliveryRepository(IncorrectDeliveryRepository incorrectDeliveryRepository) {
        this.incorrectDeliveryRepository = incorrectDeliveryRepository;
    }

    public void setReverseConverter(ReverseConverter<List<DeliveryPoint>, DeliveryPointDataList> reverseConverter) {
        this.reverseConverter = reverseConverter;
    }

    public void setConverter(Converter<DeliveryPointDataList, List<DeliveryPoint>> converter) {
        this.converter = converter;
    }
}
