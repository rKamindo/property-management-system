package com.example.tenantmanagementsystem.tenant;

public record TenantDTO (
        Long id,
        String name,
        String email,
        String gender,
        String apartmentNumber
)
{}