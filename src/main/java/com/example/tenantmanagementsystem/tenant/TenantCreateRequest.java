package com.example.tenantmanagementsystem.tenant;

public record TenantCreateRequest(
        String name,
        String email,
        String phone,
        Gender gender,
        Long apartmentId
) {
}
