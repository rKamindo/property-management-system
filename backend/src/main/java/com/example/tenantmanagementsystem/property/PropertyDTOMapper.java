package com.example.tenantmanagementsystem.property;

import java.util.function.Function;

public class PropertyDTOMapper implements Function<Property, PropertyDTO> {
    @Override
    public PropertyDTO apply(Property property) {
        return new PropertyDTO(
                property.getName(),
                property.getAddress()
        );
    }
}
