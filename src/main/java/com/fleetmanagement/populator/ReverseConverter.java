package com.fleetmanagement.populator;

public interface ReverseConverter<SOURCE,TARGET> {
    public TARGET convert(SOURCE source);
}
