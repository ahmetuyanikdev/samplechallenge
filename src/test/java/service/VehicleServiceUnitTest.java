package service;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.vehicle.VehicleDataList;
import com.fleetmanagement.model.Vehicle;
import com.fleetmanagement.repository.VehicleRepository;
import com.fleetmanagement.service.impl.VehicleServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceUnitTest {

    private Logger logger = LoggerFactory.getLogger(VehicleServiceUnitTest.class);

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private Converter<VehicleDataList, List<Vehicle>> converter;

    @Mock
    private ReverseConverter<List<Vehicle>, VehicleDataList> reverseConverter;

    private List<Vehicle> vehicles;

    private VehicleDataList vehicleDataList;

    private List<VehicleDataList.VehicleData> dataList;

    @Before
    public void setup() {
        vehicles = new LinkedList<>();
        Vehicle v1 = new Vehicle();
        v1.setPlateNumber("34ABC01");
        Vehicle v2 = new Vehicle();
        v2.setPlateNumber("34ABC02");
        VehicleDataList.VehicleData vehicleData1 = new VehicleDataList.VehicleData();
        vehicleData1.setPlateNumber(v1.getPlateNumber());
        VehicleDataList.VehicleData vehicleData2 = new VehicleDataList.VehicleData();
        vehicleData2.setPlateNumber(v2.getPlateNumber());

        vehicles.add(v1);
        vehicles.add(v2);
        dataList = new LinkedList<>();
        dataList.add(vehicleData1);
        dataList.add(vehicleData2);

        vehicleDataList = new VehicleDataList();
        vehicleDataList.setVehicles(dataList);
    }

    @Test
    public void test_getAllVehicles_Success() {
        Mockito.when(vehicleRepository.findAll()).thenReturn(vehicles);
        Mockito.when(reverseConverter.convert(vehicles)).thenReturn(vehicleDataList);
        Assert.assertFalse(vehicleService.getAllVehicles().getVehicles().isEmpty());
        Assert.assertEquals("34ABC01", vehicleService.getAllVehicles().getVehicles().get(0).getPlateNumber());
    }

    @Test
    public void test_getAllVehicles_Throws_NullPointer() {
        Mockito.when(reverseConverter.convert(anyList())).thenReturn(null);
        Assert.assertThrows(NullPointerException.class, () -> vehicleService.getAllVehicles().getVehicles());
    }

    @Test
    public void test_saveVehicle_Success(){
        Mockito.when(converter.convert(vehicleDataList)).thenReturn(vehicles);
        Mockito.when(vehicleRepository.saveAll(vehicles)).thenReturn(vehicles);
        Assert.assertFalse(vehicleService.saveVehicles(vehicleDataList).isEmpty());
    }

    @Test
    public void test_saveVehicle_Fail(){
        Mockito.when(converter.convert(vehicleDataList)).thenThrow(NullPointerException.class);
        try {
            vehicleService.saveVehicles(vehicleDataList);
        }catch (NullPointerException e){
            logger.warn("---- Exception during test method invocation ");
        }
        finally {
            Mockito.verify(vehicleRepository,Mockito.times(0)).saveAll(anyList());
        }
    }
}
