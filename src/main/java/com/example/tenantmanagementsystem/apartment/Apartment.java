package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.tenant.Tenant;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
public class Apartment {

    @Id
    @SequenceGenerator(
            name = "apartment_id_seq",
            sequenceName = "apartment_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "apartment_id_seq")
    private Long id;
    @Column(nullable = false, unique = true)
    private String apartmentNumber;
    @Column(nullable = false)
    private int numberOfRooms;
    @Column(nullable = false)
    private double rentalRate;
    @OneToMany(mappedBy = "apartment")
    private List<Tenant> tenants;

    public Apartment() {}

    public Apartment(Long id, String apartmentNumber, int numberOfRooms, double rentalRate, List<Tenant> tenants) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
        this.rentalRate = rentalRate;
        this.tenants = tenants;
    }

    public Apartment(String apartmentNumber, int numberOfRooms, double rentalRate, List<Tenant> tenants) {
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
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

    public double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return numberOfRooms == apartment.numberOfRooms && Double.compare(apartment.rentalRate, rentalRate) == 0 && Objects.equals(id, apartment.id) && Objects.equals(apartmentNumber, apartment.apartmentNumber) && Objects.equals(tenants, apartment.tenants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apartmentNumber, numberOfRooms, rentalRate, tenants);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rentalRate=" + rentalRate +
                ", tenants=" + tenants +
                '}';
    }
}
