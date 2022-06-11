package com.fleetmanagement.test.converter;

import com.fleetmanagement.converter.impl.VehicleDataModelConverter;
import com.fleetmanagement.data.VehicleDataList;
import com.fleetmanagement.exception.DataConversionException;
import com.fleetmanagement.model.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class VehicleDataModelConverterTest {

    @InjectMocks
    private VehicleDataModelConverter vehicleDataModelConverter;

    @Mock
    private MessageSource messageSource;

    private VehicleDataList vehicleDataList;

    @Before
    public void setup() {
        vehicleDataList = new VehicleDataList();
        VehicleDataList.VehicleData vehicleData = new VehicleDataList.VehicleData();
        vehicleData.setPlateNumber("34ABC34");
        vehicleDataList.setVehicles(Collections.singletonList(vehicleData));
    }

    @Test
    public void test_convertVehicleDataToModel_success() {
        List<Vehicle> vehicles = vehicleDataModelConverter.convert(vehicleDataList);
        Assert.assertEquals(vehicles.get(0).getPlateNumber(), "34ABC34");
    }

    @Test
    public void test_convertVehicleDataToModel_fail() {
        Mockito.when(messageSource.getMessage("vehicle.plate.number.incorrect", null, Locale.ENGLISH)).thenReturn("Plate number incorrect !, Sample format is 34AU34");
        vehicleDataList.getVehicles().get(0).setPlateNumber("dsdsd");
        List<Vehicle> vehicles = new LinkedList<>();
        try {
            vehicles = vehicleDataModelConverter.convert(vehicleDataList);
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), DataConversionException.class);
        } finally {
            Assert.assertTrue(vehicles.isEmpty());
        }
    }
}
