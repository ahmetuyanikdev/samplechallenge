package com.fleetmanagement.service.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.exception.NoDataFoundException;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.repository.RouteRepository;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    @Qualifier("routeDataModelConverter")
    private Converter<RoutePlanData, List<Route>> converter;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private DeliveryPointRepository deliveryPointRepository;

    @Autowired
    @Qualifier("routeModelDataConverter")
    private ReverseConverter<List<Route>, RoutePlanData> reverseConverter;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<Route> saveRoutes(RoutePlanData routePlanData) {
        List<Route> routes = converter.convert(routePlanData);
        routes = routeRepository.saveAll(routes);
        updateShipmentsStatuses(routes);
        return routes;
    }

    @Override
    public RoutePlanData unloadDeliveries(String plateNumber){
        List<Route> routes = routeRepository.findAllByVehiclePlateNumber(plateNumber);
        if (routes.isEmpty()) {
            throw new NoDataFoundException(messageSource.getMessage(
                    "vehicle.route.data.not-found", null, Locale.ENGLISH));
        }
        return reverseConverter.convert(routes);
    }

    private void updateShipmentsStatuses(List<Route> routes) {
        routes.forEach(route -> {
            shipmentRepository.saveAll(route.getDeliveries());
            deliveryPointRepository.save(route.getDeliveryPoint());
        });
    }


    public void setConverter(Converter<RoutePlanData, List<Route>> converter) {
        this.converter = converter;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setReverseConverter(ReverseConverter<List<Route>, RoutePlanData> reverseConverter) {
        this.reverseConverter = reverseConverter;
    }

    public void setDeliveryPointRepository(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }

    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }
}
