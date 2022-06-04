package com.fleetmanagement.service.impl;

import com.fleetmanagement.converter.Converter;
import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.converter.impl.ItemDataModelConverter;
import com.fleetmanagement.data.item.ItemDataList;
import com.fleetmanagement.model.item.Item;
import com.fleetmanagement.repository.ItemRepository;
import com.fleetmanagement.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    @Qualifier("itemDataModelConverter")
    private Converter<ItemDataList,List<Item>> converter;

    @Autowired
    @Qualifier("itemModelDataConverter")
    private ReverseConverter<List<Item>,ItemDataList> reverseConverter;


    @Override
    public List<Item> saveItems(ItemDataList itemDataList) {
        List<Item> items = converter.convert(itemDataList);
        return itemRepository.saveAll(items);
    }

    @Override
    public ItemDataList getAllItems() {
        List<Item> items = itemRepository.findAll();
        return reverseConverter.convert(items);
    }

    @Override
    public Item getItemByBarcode(String barcode) {
        return itemRepository.getItemByBarcode(barcode);
    }

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void setConverter(ItemDataModelConverter converter) {
        this.converter = converter;
    }
}
