package com.example.tenantmanagementsystem.model;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apartmentNumber;
    private int numberOfRooms;
    private String address;
    private double rentalRate;
    @OneToMany(mappedBy = "apartment")
    private List<Tenant> tenants;

    public Apartment() {}

    public Apartment(String apartmentNumber, int numberOfRooms, String address, double rentalRate, List<Tenant> tenants) {
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.rentalRate = rentalRate;
        this.tenants = tenants;
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

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenant) {
        this.tenants = tenant;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", rentalRate=" + rentalRate +
                ", tenants=" + tenants +
                '}';
    }


}
