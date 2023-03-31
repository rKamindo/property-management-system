package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.property.Property;
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
    private double rent;

    private boolean isAvailable;
    private boolean isOccupied;
    @JoinColumn(name = "tenant_id")
    @OneToOne(cascade = CascadeType.PERSIST)
    private Tenant tenant;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    public Apartment() {
    }

    public Apartment(String apartmentNumber, int numberOfRooms, double rent, boolean isAvailable, boolean isOccupied, Tenant tenant, Property property) {
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
        this.rent = rent;
        this.isAvailable = isAvailable;
        this.isOccupied = isOccupied;
        this.tenant = tenant;
        this.property = property;
    }

    public Apartment(Long id, String apartmentNumber, int numberOfRooms, double rent, boolean isAvailable, boolean isOccupied, Tenant tenant, Property property) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.numberOfRooms = numberOfRooms;
        this.rent = rent;
        this.isAvailable = isAvailable;
        this.isOccupied = isOccupied;
        this.tenant = tenant;
        this.property = property;
    }

    public Apartment(ApartmentCreateRequest apartmentCreateRequest) {
        this.apartmentNumber = apartmentCreateRequest.apartmentNumber();
        this.numberOfRooms = apartmentCreateRequest.numberOfRooms();
        this.rent = apartmentCreateRequest.rent();
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
        if (tenant == null) {
            if (this.tenant != null) {
                this.tenant.setApartment(null);
                isOccupied = false;
            }
        } else {
            tenant.setApartment(this);
            isOccupied = true;
        }
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return numberOfRooms == apartment.numberOfRooms && Double.compare(apartment.rent, rent) == 0 && isAvailable == apartment.isAvailable && isOccupied == apartment.isOccupied && Objects.equals(id, apartment.id) && Objects.equals(apartmentNumber, apartment.apartmentNumber) && Objects.equals(tenant, apartment.tenant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apartmentNumber, numberOfRooms, rent, isAvailable, isOccupied, tenant);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rent=" + rent +
                ", isAvailable=" + isAvailable +
                ", isOccupied=" + isOccupied +
                ", tenant=" + tenant +
                '}';
    }
}