package com.fleetmanagement.test.service;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.item.ItemAssignmentDataList;
import com.fleetmanagement.data.item.ItemDataList;
import com.fleetmanagement.model.DeliveryPoint;
import com.fleetmanagement.model.item.Bag;
import com.fleetmanagement.model.item.Item;
import com.fleetmanagement.model.item.Package;
import com.fleetmanagement.repository.ItemRepository;
import com.fleetmanagement.service.impl.ItemServiceImpl;
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
public class ItemServiceUnitTest {

    private Logger logger = LoggerFactory.getLogger(ItemServiceUnitTest.class);

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private Converter<ItemDataList, List<Item>> converter;

    @Mock
    private ReverseConverter<List<Item>, ItemDataList> reverseConverter;

    @Mock
    private Converter<ItemAssignmentDataList,List<Item>> itemAssignmentConverter;

    private ItemDataList itemDataList;

    private List<Item> items;

    private ItemAssignmentDataList itemAssignmentDataList;

    @Before
    public void setup() {
        itemDataList = new ItemDataList();
        items = new LinkedList<>();
        itemAssignmentDataList = new ItemAssignmentDataList();

        List<ItemDataList.ItemData> dataList = new LinkedList<>();

        Bag bag1 = new Bag();
        Package pack1 = new Package();
        DeliveryPoint branch = new DeliveryPoint();

        branch.setId(1);
        branch.setType("Branch");
        bag1.setBarcode("C725799");
        bag1.setDeliveryPoint(branch);

        pack1.setBarcode("P8988000120");
        pack1.setDeliveryPoint(branch);
        pack1.setVolumetricWeight(9);
        pack1.setBag(bag1);

        items.add(bag1);
        items.add(pack1);

        ItemDataList.ItemData itemData1 = new ItemDataList.ItemData();
        itemData1.setBarcode("C725799");
        itemData1.setDeliveryPoint(1);

        dataList.add(itemData1);

        ItemDataList.ItemData itemData2 = new ItemDataList.ItemData();
        itemData2.setBarcode("P8988000120");
        itemData2.setDeliveryPoint(1);
        itemData2.setVolumetricWeight(9);

        dataList.add(itemData2);
        itemDataList.setItems(dataList);

        ItemAssignmentDataList.ItemAssignment itemAssignment1 = new ItemAssignmentDataList.ItemAssignment();
        itemAssignment1.setBarcode("P8988000120");
        itemAssignment1.setBagBarcode("C725799");
        List<ItemAssignmentDataList.ItemAssignment> itemAssignments = new LinkedList<>();
        itemAssignments.add(itemAssignment1);

    }

    @Test
    public void test_getAllItems_success() {
        Mockito.when(itemRepository.findAll()).thenReturn(items);
        Mockito.when(reverseConverter.convert(items)).thenReturn(itemDataList);
        ItemDataList list = itemService.getAllItems();
        Assert.assertEquals(list.getItems().size(), 2);
        Assert.assertTrue(list.getItems().stream().anyMatch(i -> i.getBarcode().equalsIgnoreCase("P8988000120")));
    }

    @Test
    public void test_getAllItems_fail() {
        Mockito.when(itemRepository.findAll()).thenThrow(NullPointerException.class);
        try {
            itemService.getAllItems();
        } catch (NullPointerException e) {
            logger.warn("---- Exception during test method invocation ");
        } finally {
            Mockito.verify(reverseConverter, Mockito.never()).convert(items);
        }
    }

    @Test
    public void test_saveItems_success(){
        Mockito.when(converter.convert(itemDataList)).thenReturn(items);
        Mockito.when(itemRepository.saveAll(items)).thenReturn(items);
        Assert.assertFalse(itemService.saveItems(itemDataList).isEmpty());
    }

    @Test
    public void test_saveItems_fail(){
        Mockito.when(converter.convert(itemDataList)).thenThrow(NumberFormatException.class);
        try{
            itemService.saveItems(itemDataList);
        }catch (Exception e){
            logger.warn("---- Exception during test method invocation ");
        }finally {
            Mockito.verify(itemRepository,Mockito.never()).saveAll(items);
        }
    }

    @Test
    public void test_assignItems_success(){
        Mockito.when(itemAssignmentConverter.convert(itemAssignmentDataList)).thenReturn(items);
        Mockito.when(itemRepository.saveAll(items)).thenReturn(items);
        Assert.assertTrue(itemService.assignItems(itemAssignmentDataList).stream().anyMatch(item -> item.getBarcode().equals("P8988000120")));
    }

}
