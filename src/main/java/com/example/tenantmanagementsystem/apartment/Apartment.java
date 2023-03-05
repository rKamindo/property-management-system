package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.tenant.Tenant;
import jakarta.persistence.*;

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
    private double rent;
    @OneToOne(mappedBy = "apartment")
    private Tenant tenant;

    public Apartment() {}

    public Apartment(Long id, String apartmentNumber, int numberOfRooms, double rent, Tenant tenant) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
        this.rent = rent;
        this.tenant = tenant;
    }

    public Apartment(String apartmentNumber, int numberOfRooms, double rent, Tenant tenant) {
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
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

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return numberOfRooms == apartment.numberOfRooms && Double.compare(apartment.rent, rent) == 0 && Objects.equals(id, apartment.id) && Objects.equals(apartmentNumber, apartment.apartmentNumber) && Objects.equals(tenant, apartment.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apartmentNumber, numberOfRooms, rent, tenant);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rent=" + rent +
                ", tenant=" + tenant +
                '}';
    }
}
