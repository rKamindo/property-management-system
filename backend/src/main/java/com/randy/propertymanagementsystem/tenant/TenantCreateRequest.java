package com.randy.propertymanagementsystem.tenant;

public record TenantCreateRequest(
        String name,
        String email,
        String phone,
        Gender gender
) {
}
