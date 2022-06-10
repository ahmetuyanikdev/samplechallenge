package com.fleetmanagement.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.controller.DeliveryPointController;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.data.IncorrectDeliveryDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.service.DeliveryPointService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeliveryPointControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DeliveryPointController deliveryPointController;

    @Mock
    private DeliveryPointService deliveryPointService;

    private DeliveryPointDataList deliveryPointDataList;

    private List<DeliveryPoint> deliveryPoints;

    private String deliveryPointPostPayload;

    private IncorrectDeliveryDataList incorrectDeliveryDataList;

    @Before
    public void setup() throws JsonProcessingException {
        deliveryPointDataList = new DeliveryPointDataList();
        deliveryPointController.setDeliveryPointService(deliveryPointService);
        deliveryPoints = new LinkedList<>();
        incorrectDeliveryDataList = new IncorrectDeliveryDataList();

        DeliveryPointDataList.DeliveryPointData deliveryPointData = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData.setType(DeliveryPointType.Branch.name());
        deliveryPointData.setId(DeliveryPointType.Branch.getValue());

        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setType(deliveryPoint.getType());
        deliveryPoint.setId(deliveryPoint.getId());
        deliveryPoints.add(deliveryPoint);

        List<DeliveryPointDataList.DeliveryPointData> dataList = new LinkedList<>();
        dataList.add(deliveryPointData);
        deliveryPointDataList.setDeliveryPoints(dataList);
        deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        deliveryPointPostPayload = super.getStringPayload(deliveryPointDataList);

        incorrectDeliveryDataList.setDeliveryPoint(1);
        IncorrectDeliveryDataList.ShipmentData shipmentData = new IncorrectDeliveryDataList.ShipmentData("P8988000121");
        incorrectDeliveryDataList.setDeliveries(Collections.singletonList(shipmentData));
    }

    @Test
    public void test_saveDeliveryPoints_success() throws Exception {
        Mockito.when(deliveryPointService.saveDeliveryPoints(deliveryPointDataList)).thenReturn(deliveryPoints);
        mvc.perform(post("/delivery-points").contentType(MediaType.APPLICATION_JSON).
                content(deliveryPointPostPayload).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void test_saveDeliveryPoint_fail() throws Exception {
        deliveryPointDataList.getDeliveryPoints().get(0).setId(1);
        deliveryPointDataList.getDeliveryPoints().get(0).setType(null);
        deliveryPointPostPayload = super.getStringPayload(deliveryPointDataList);
        mvc.perform(post("/delivery-points").contentType(MediaType.APPLICATION_JSON).
                content(deliveryPointPostPayload).characterEncoding("utf-8")).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    public void test_getAllDeliveryPoints_success() throws Exception {
        Mockito.when(deliveryPointService.getAllDeliveryPoints()).thenReturn(deliveryPointDataList);
        mvc.perform(get("/delivery-points").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andExpect(content().
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("deliveryPoints[0].type").value(DeliveryPointType.Branch.name()));
    }

    @Test
    public void test_getIncorrectDeliveries_success() throws Exception {
        int id = 1;
        Mockito.when(deliveryPointService.getAllIncorrectlyDeliveriesForDeliveryPoint(id)).thenReturn(incorrectDeliveryDataList);
        mvc.perform(get("/delivery-points/incorrectShipments/1").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andExpect(content().
                contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("deliveries[0].barcode").value("P8988000121"));
    }
}
