package com.randy.propertymanagementsystem.property;

public record PropertyUpdateRequest(
        String name,
        String address
) {
}
