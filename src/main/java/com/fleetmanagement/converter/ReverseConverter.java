package com.fleetmanagement.converter;

public interface ReverseConverter<SOURCE,TARGET> {
    public TARGET convert(SOURCE source);
}
