package com.fleetmanagement.populator;

public interface Converter<SOURCE,TARGET> {
    public TARGET convert(SOURCE source);
}
