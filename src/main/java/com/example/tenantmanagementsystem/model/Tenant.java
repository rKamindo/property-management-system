package com.example.tenantmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String phoneNumber;
    @Column(nullable = false, updatable = false)
    private String tenantCode;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    public Tenant() {}

    public Tenant(String firstName, String lastName, String phoneNumber, String tenantCode, Apartment apartment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.tenantCode = tenantCode;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long number) {
        id = number;
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

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                ", apartment=" + apartment +
                '}';
    }
}
