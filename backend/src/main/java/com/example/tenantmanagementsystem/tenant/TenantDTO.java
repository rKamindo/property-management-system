package com.example.tenantmanagementsystem.tenant;

public record TenantDTO (
        Long id,
        String name,
        String email,
        Gender gender,
        String apartmentNumber
)
{}