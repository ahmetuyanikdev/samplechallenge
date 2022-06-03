package service;

import com.fleetmanagement.constant.DeliveryPointType;
import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.DeliveryPointDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.repository.DeliveryPointRepository;
import com.fleetmanagement.service.impl.DeliveryPointServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class DeliveryPointServiceUnitTest {

    private Logger logger = LoggerFactory.getLogger(DeliveryPointServiceUnitTest.class);

    @InjectMocks
    private DeliveryPointServiceImpl deliveryPointService;

    @Mock
    private DeliveryPointRepository deliveryPointRepository;

    @Mock
    private Converter<DeliveryPointDataList, List<DeliveryPoint>> converter;

    @Mock
    private ReverseConverter<List<DeliveryPoint>, DeliveryPointDataList> reverseConverter;

    private DeliveryPointDataList deliveryPointDataList;

    private List<DeliveryPointDataList.DeliveryPointData> dataList;

    private List<DeliveryPoint> deliveryPoints;

    @Before
    public void setup() {
        deliveryPointDataList = new DeliveryPointDataList();
        dataList = new LinkedList<>();
        deliveryPoints = new LinkedList<>();

        DeliveryPoint deliveryPoint1 = new DeliveryPoint();
        deliveryPoint1.setId(1);
        deliveryPoint1.setType(DeliveryPointType.Branch.name());

        DeliveryPoint deliveryPoint2 = new DeliveryPoint();
        deliveryPoint2.setId(2);
        deliveryPoint2.setType(DeliveryPointType.Distribution_Center.name());

        deliveryPoints.add(deliveryPoint1);
        deliveryPoints.add(deliveryPoint2);

        DeliveryPointDataList.DeliveryPointData deliveryPointData1 = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData1.setId(1);
        deliveryPointData1.setType(DeliveryPointType.Branch.name());

        DeliveryPointDataList.DeliveryPointData deliveryPointData2 = new DeliveryPointDataList.DeliveryPointData();
        deliveryPointData2.setId(2);
        deliveryPointData2.setType(DeliveryPointType.Distribution_Center.name());

        dataList.add(deliveryPointData1);
        dataList.add(deliveryPointData2);
        deliveryPointDataList.setDeliveryPoints(dataList);
    }

    @Test
    public void test_saveDeliveryPoints_success() {
        Mockito.when(converter.convert(deliveryPointDataList)).thenReturn(deliveryPoints);
        Mockito.when(deliveryPointRepository.saveAll(deliveryPoints)).thenReturn(deliveryPoints);
        List<DeliveryPoint> deliveryPoints = deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        Assert.assertFalse(deliveryPoints.isEmpty());
        Assert.assertEquals(deliveryPoints.get(1).getType(), DeliveryPointType.Distribution_Center.name());
    }

    @Test
    public void test_saveDeliveryPoints_fail() {
        Mockito.when(converter.convert(deliveryPointDataList)).thenThrow(NullPointerException.class);
        try {
            deliveryPointService.saveDeliveryPoints(deliveryPointDataList);
        } catch (NullPointerException e) {
            logger.warn("---- Exception during test method invocation ");
        } finally {
            Mockito.verify(deliveryPointRepository, Mockito.times(0)).saveAll(deliveryPoints);
        }
    }

    @Test
    public void test_getAllDeliveryPoints_success() {
        Mockito.when(deliveryPointRepository.findAll()).thenReturn(deliveryPoints);
        Mockito.when(reverseConverter.convert(deliveryPoints)).thenReturn(deliveryPointDataList);
        DeliveryPointDataList allDeliveryPoints = deliveryPointService.getAllDeliveryPoints();
        Assert.assertEquals(allDeliveryPoints.getDeliveryPoints().size(), 2);
    }
}
