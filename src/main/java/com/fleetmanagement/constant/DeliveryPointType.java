package com.fleetmanagement.constant;

public enum DeliveryPointType {
    Branch(1),
    Distribution_Center(2),
    Transfer_Center(3);

    private final int value;

    DeliveryPointType(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }
}
