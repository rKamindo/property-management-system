package com.randy.tenantmanagementsystem.tenant;

public record TenantUpdateRequest(
    String name,
    String email,
    String phone
    ) {

}
