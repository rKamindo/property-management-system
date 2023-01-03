package com.example.tenantmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Apartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String apartmentNumber;
    private double rent;
    @OneToOne(mappedBy = "apartment")
    @JsonIgnore
    private Tenant tenant;

    public Apartment() {}
    public Apartment(String apartmentNumber, double rent, Tenant tenant) {
        this.apartmentNumber = apartmentNumber;
        this.rent = rent;
        this.tenant = tenant;
    }

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

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", rent=" + rent +
                ", tenant=" + tenant +
                '}';
    }
}
