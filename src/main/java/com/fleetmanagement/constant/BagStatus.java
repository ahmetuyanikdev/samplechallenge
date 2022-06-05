package com.fleetmanagement.constant;

public enum BagStatus {

    Created(1),Loaded(2),Unloaded(3);

    private final int value;

    BagStatus(int val) {
        this.value = val;
    }
    public int getValue(){
        return value;
    }
}
