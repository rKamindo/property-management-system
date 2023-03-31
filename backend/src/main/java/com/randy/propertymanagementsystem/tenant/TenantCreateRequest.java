package com.randy.tenantmanagementsystem.tenant;

public record TenantCreateRequest(
        String name,
        String email,
        String phone,
        Gender gender
) {
}