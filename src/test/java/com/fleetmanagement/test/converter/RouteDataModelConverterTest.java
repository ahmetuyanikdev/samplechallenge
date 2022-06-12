package com.fleetmanagement.test.converter;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.converter.impl.RouteDataModelConverter;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.repository.IncorrectDeliveryRepository;
import com.fleetmanagement.repository.ShipmentRepository;
import com.fleetmanagement.repository.VehicleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteDataModelConverterTest {

    @InjectMocks
    private RouteDataModelConverter routeDataModelConverter;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private DeliveryPointRepository deliveryPointRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private IncorrectDeliveryRepository incorrectDeliveryRepository;

    @Mock
    private MessageSource messageSource;

    private VehicleDataList vehicleDataList;

    private RoutePlanData routePlanData;

    private DeliveryPoint deliveryPoint;

    private Vehicle vehicle;

    private Shipment shipment;

    @Before
    public void setup() {
        routePlanData = new RoutePlanData();
        vehicleDataList = new VehicleDataList();
        VehicleDataList.VehicleData vehicleData = new VehicleDataList.VehicleData();
        vehicleData.setPlateNumber("34TL34");
        vehicleDataList.setVehicles(Collections.singletonList(vehicleData));

        RouteData.ShipmentInformation shipmentInformation = new RouteData.ShipmentInformation();
        shipmentInformation.setBarcode("P7988000123");
        RouteData routeData = new RouteData();
        routeData.setDeliveryPoint(1);
        routeData.setDeliveries(Collections.singletonList(shipmentInformation));

        routePlanData.setRoute(Collections.singletonList(routeData));
        routePlanData.setPlate("34TL34");

        deliveryPoint = new DeliveryPoint();
        deliveryPoint.setType(DeliveryPointType.Branch.name());
        deliveryPoint.setId(DeliveryPointType.Branch.getValue());
        deliveryPoint.setRoutes(new HashSet<>());
        vehicle = new Vehicle();
        vehicle.setPlateNumber("34TL34");
        shipment = new Shipment();
        shipment.setRoutes(new HashSet<>());
        shipment.setBarcode("P7988000123");
        shipment.setDeliveryPoint(deliveryPoint);
    }

    @Test
    public void test1_convertRouteData_success() {

        Mockito.when(deliveryPointRepository.findById(DeliveryPointType.Branch.getValue()))
                .thenReturn(Optional.of(deliveryPoint));

        Mockito.when(vehicleRepository.findById("34TL34")).thenReturn(Optional.of(vehicle));

        Mockito.when(shipmentRepository.findAllById(Collections.singleton("P7988000123"))).
                thenReturn(Collections.singletonList(shipment));

        List<Route> routes = routeDataModelConverter.convert(routePlanData);
        Assert.assertEquals(routes.get(0).getDeliveryPoint().getType(), DeliveryPointType.Branch.name());
    }

    @Test
    public void test_convertRouteData_fail() {
        routePlanData.getRoute().get(0).setDeliveryPoint(0);
        Mockito.when(messageSource.getMessage("route.data.violation", null, Locale.ENGLISH)).thenReturn("Route Data invalid");
        List<Route> routes = new LinkedList<>();
        try {
            routes = routeDataModelConverter.convert(routePlanData);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), DataConversionException.class);
            Assert.assertEquals(e.getMessage(), "Route Data invalid");
        }
    }
}
