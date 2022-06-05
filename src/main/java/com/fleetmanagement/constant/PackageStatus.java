package com.fleetmanagement.constant;


public enum PackageStatus {
    Created(1),
    Loaded_Into_Bag(2),
    Loaded(3),
    Unloaded(4);
    int value;
    PackageStatus(int val){
        this.value=val;
    }

    public int getValue() {
        return value;
    }
}
