package com.fleetmanagement.converter.impl;

import com.fleetmanagement.converter.ReverseConverter;
import com.fleetmanagement.data.item.ItemDataList;
import com.fleetmanagement.model.item.Bag;
import com.fleetmanagement.model.item.Item;
import com.fleetmanagement.model.item.Package;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Qualifier("itemModelDataConverter")
public class ItemModelDataConverter implements ReverseConverter<List<Item>, ItemDataList> {

    private Map<Class, InternalConverter> converterMap;

    @PostConstruct
    public void setInternalConverter() {
        converterMap = new HashMap<>();
        converterMap.put(Bag.class, new BagInternalConverter());
        converterMap.put(Package.class, new PackageInternalConverter());
    }

    @Override
    public ItemDataList convert(List<Item> items) {
        List<ItemDataList.ItemData> dataList = items.stream()
                .map(i -> converterMap.get(i.getClass()).
                convert(i)).collect(Collectors.toList());
        ItemDataList itemDataList = new ItemDataList();
        itemDataList.setItems(dataList);
        return itemDataList;
    }

    public abstract class InternalConverter {
        protected abstract ItemDataList.ItemData convert(Item item);
    }

    public class BagInternalConverter extends InternalConverter {

        @Override
        protected ItemDataList.ItemData convert(Item item) {
            ItemDataList.ItemData itemData = new ItemDataList.ItemData();
            itemData.setBarcode(item.getBarcode());
            itemData.setDeliveryPoint(item.getDeliveryPoint().getId());
            return itemData;
        }
    }

    public class PackageInternalConverter extends InternalConverter {

        @Override
        protected ItemDataList.ItemData convert(Item item) {
            ItemDataList.ItemData itemData = new ItemDataList.ItemData();
            itemData.setBarcode(item.getBarcode());
            itemData.setDeliveryPoint(item.getDeliveryPoint().getId());
            itemData.setVolumetricWeight(((Package) item).getVolumetricWeight());
            return itemData;
        }
    }

}
