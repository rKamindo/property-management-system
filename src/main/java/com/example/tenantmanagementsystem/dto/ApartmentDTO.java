package com.example.tenantmanagementsystem.dto;

import java.util.List;

public class ApartmentDTO {

    private Long id;
    private String apartmentNumber;
    private Integer numberOfRooms;
    private String address;
    private List<TenantLightDTO> tenants;

    private ApartmentDTO() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<TenantLightDTO> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantLightDTO> tenants) {
        this.tenants = tenants;
    }
}