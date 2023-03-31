package com.randy.tenantmanagementsystem.property;

public record PropertyUpdateRequest(
        String name,
        String address
) {
}
