package com.example.tenantmanagementsystem.dto;

import com.example.tenantmanagementsystem.model.Tenant;

import java.util.UUID;

public class TenantDTO {

    private Long id;
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Long apartmentId;
    private String apartmentNumber;

    private TenantDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long id) {
        this.apartmentId = id;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}