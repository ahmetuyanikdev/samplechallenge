package com.fleetmanagement.test.controller;


import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.controller.ShipmentController;
import com.fleetmanagement.data.shipment.ShipmentAssignmentDataList;
import com.fleetmanagement.data.shipment.ShipmentDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.shipment.Shipment;
import com.fleetmanagement.service.ShipmentService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShipmentControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShipmentController shipmentController;

    @Mock
    private ShipmentService shipmentService;

    private ShipmentDataList shipmentDataList;

    private List<Shipment> shipments;

    private ShipmentAssignmentDataList shipmentAssignmentDataList;

    private List<ShipmentDataList.ShipmentData> shipmentDataAfterAssignment;

    @Before
    public void setup() {

        shipmentDataList = new ShipmentDataList();
        shipments = new LinkedList<>();
        shipmentController.setShipmentService(shipmentService);
        shipmentAssignmentDataList = new ShipmentAssignmentDataList();
        shipmentDataAfterAssignment = new LinkedList<>();
        ShipmentDataList.ShipmentData shipmentData = new ShipmentDataList.ShipmentData();
        shipmentData.setDeliveryPoint(1);
        shipmentData.setBarcode("C725799");

        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setId(shipmentData.getDeliveryPoint());
        deliveryPoint.setType(DeliveryPointType.Branch.name());

        Shipment shipment = new Shipment();
        shipment.setBarcode(shipmentData.getBarcode());
        shipment.setDeliveryPoint(deliveryPoint);

        shipmentDataList.setShipments(Collections.singletonList(shipmentData));

        ShipmentAssignmentDataList.ShipmentAssignment shipmentAssignment = new ShipmentAssignmentDataList.ShipmentAssignment();
        shipmentAssignment.setBagBarcode("C725799");
        shipmentAssignment.setBarcode("P8988000122");

        shipmentAssignmentDataList.setShipmentAssignments(Collections.singletonList(shipmentAssignment));

        ShipmentDataList.ShipmentData shipmentData1 = new ShipmentDataList.ShipmentData();
        shipmentData1.setBarcode("C725799");
        shipmentData1.setDeliveryPoint(1);
        shipmentData1.setStatus(3);

        ShipmentDataList.ShipmentData shipmentData2 = new ShipmentDataList.ShipmentData();
        shipmentData2.setBarcode("P8988000122");
        shipmentData2.setDeliveryPoint(1);
        shipmentData2.setStatus(3);

        shipmentDataAfterAssignment.add(shipmentData1);
        shipmentDataAfterAssignment.add(shipmentData2);
    }

    @Test
    public void test_saveShipments_success() throws Exception {
        Mockito.when(shipmentService.saveShipments(shipmentDataList)).thenReturn(shipments);
        String shipmentPostPayload = super.getStringPayload(shipmentDataList);
        mvc.perform(post("/shipments").contentType(MediaType.APPLICATION_JSON).
                content(shipmentPostPayload).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void test_saveShipments_fail() throws Exception {
        shipmentDataList.getShipments().get(0).setBarcode(null);
        String shipmentPostPayload = super.getStringPayload(shipmentDataList);
        mvc.perform(post("/shipments").contentType(MediaType.APPLICATION_JSON).
                content(shipmentPostPayload).characterEncoding("utf-8")).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    public void test_assignPacksToBags_success() throws Exception {
        ShipmentDataList assignmentResult = new ShipmentDataList();
        assignmentResult.setShipments(shipmentDataAfterAssignment);
        Mockito.when(shipmentService.assignShipments(any())).thenReturn(assignmentResult);
        String shipmentAssignmentPayload = super.getStringPayload(shipmentAssignmentDataList);
        mvc.perform(patch("/shipments/assignments").contentType(MediaType.APPLICATION_JSON).
                content(shipmentAssignmentPayload).characterEncoding("utf-8")).andExpect(status().isOk())
                .andExpect(jsonPath("shipments[1].barcode").value("P8988000122")).andReturn();
    }

    @Test
    public void test_assignPacksToBags_fail() throws Exception {
        shipmentAssignmentDataList.getShipmentAssignments().get(0).setBarcode(null);
        String shipmentAssignmentPayload = super.getStringPayload(shipmentAssignmentDataList);
        mvc.perform(patch("/shipments/assignments").contentType(MediaType.APPLICATION_JSON).
                content(shipmentAssignmentPayload).characterEncoding("utf-8")).andExpect(status().is4xxClientError())
                .andReturn();
    }

}
