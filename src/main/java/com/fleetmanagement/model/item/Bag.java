package com.fleetmanagement.model.item;

import java.util.List;


public class Bag extends Item {

    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        items.add(item);
    }
    public void removeItem(Item item){
        items.remove(item);
    }
}
