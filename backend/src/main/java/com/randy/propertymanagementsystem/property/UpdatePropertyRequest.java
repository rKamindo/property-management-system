package com.randy.propertymanagementsystem.property;

public record UpdatePropertyRequest(
        String name,
        String address
) {
}
