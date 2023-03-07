package com.example.tenantmanagementsystem.tenant;

public record TenantUpdateRequest(
    String name,
    String email,
    String phone
    ) {

}
