package com.fleetmanagement.model;

public class DeliveryPoint {

    private int id;
    private String type;

    /*
    Branch	1
    Distribution Center	2
    Transfer Center	3
    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
