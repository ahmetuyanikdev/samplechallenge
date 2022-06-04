package com.fleetmanagement.repository;

import com.fleetmanagement.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,String> {
    List<Item> getItemsByDeliveryPoint(int id);
    Item getItemByBarcode(String barcode);
}
