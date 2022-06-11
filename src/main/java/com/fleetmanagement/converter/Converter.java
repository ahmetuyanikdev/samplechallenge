package com.fleetmanagement.converter;

public interface Converter<SOURCE,TARGET> {
    boolean violation(SOURCE source);
    public TARGET convert(SOURCE source);
}
