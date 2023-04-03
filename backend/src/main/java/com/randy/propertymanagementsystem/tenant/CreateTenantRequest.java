package com.randy.propertymanagementsystem.tenant;

public record CreateTenantRequest(
        String name,
        String email,
        String phone,
        Gender gender
) {
}
