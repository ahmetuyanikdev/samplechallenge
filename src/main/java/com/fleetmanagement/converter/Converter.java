package com.fleetmanagement.converter;

public interface Converter<SOURCE,TARGET> {
    public TARGET convert(SOURCE source);
}
