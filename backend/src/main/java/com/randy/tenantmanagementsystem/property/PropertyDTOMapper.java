package com.randy.tenantmanagementsystem.property;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PropertyDTOMapper implements Function<Property, PropertyDTO> {
    @Override
    public PropertyDTO apply(Property property) {
        return new PropertyDTO(
                property.getName(),
                property.getAddress()
        );
    }
}
