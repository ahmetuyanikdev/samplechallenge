package com.fleetmanagement.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fleetmanagement.controller.RouteController;
import com.fleetmanagement.data.RouteData;
import com.fleetmanagement.data.RoutePlanData;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.Route;
import com.fleetmanagement.service.RouteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RouteControllerTest extends ControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private RouteController routeController;

    @Mock
    private RouteService routeService;

    private RoutePlanData routePlanData;

    private List<Route> routes;

    @Before
    public void setup() {
        routeController.setRouteService(routeService);
        routePlanData = new RoutePlanData();
        routePlanData.setPlate("34TL34");
        routes = new LinkedList<>();

        Route route = new Route();
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setId(1);
        route.setDeliveryPoint(deliveryPoint);
        routes.add(route);

        RouteData routeData = new RouteData();
        routeData.setDeliveryPoint(1);
        RouteData.ShipmentInformation shipmentInformation = new RouteData.ShipmentInformation();
        shipmentInformation.setBarcode("P7988000121");

        routeData.setDeliveries(Collections.singletonList(shipmentInformation));
        routePlanData.setRoute(Collections.singletonList(routeData));
    }

    @Test
    public void test_saveRoutes_success() throws Exception {
        String routePayloadPost = super.getStringPayload(routePlanData);
        Mockito.when(routeService.saveRoutes(routePlanData)).thenReturn(routes);
        mvc.perform(post("/routes").contentType(MediaType.APPLICATION_JSON).
                content(routePayloadPost).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void test_saveRoutes_fail() throws Exception {
        routePlanData.setPlate("111111");
        String routePayloadPost = super.getStringPayload(routePlanData);
        mvc.perform(post("/routes").contentType(MediaType.APPLICATION_JSON).
                content(routePayloadPost).characterEncoding("utf-8")).andExpect(status().is4xxClientError()).andReturn();

    }


}
