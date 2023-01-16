package com.example.tenantmanagementsystem.dto;

import com.example.tenantmanagementsystem.model.Tenant;

public class TenantLightDTO {

    private Long id;
    private String firstName;
    private String lastName;

    private TenantLightDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
