package com.example.tenantmanagementsystem.model;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
public class Tenant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String lastName;

    private String apartmentNumber;
    private String phoneNumber;
    @Column(nullable = false, updatable = false)
    private String tenantCode;

    public Tenant() {}

    public Tenant(String firstName, String lastName, String apartmentNumber, String phoneNumber, String tenantCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
        this.tenantCode = tenantCode;
    }

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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                '}';
    }
}
