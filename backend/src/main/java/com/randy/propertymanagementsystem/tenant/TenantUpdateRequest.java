package com.randy.propertymanagementsystem.tenant;

public record TenantUpdateRequest(
    String name,
    String email,
    String phone
    ) {

}
