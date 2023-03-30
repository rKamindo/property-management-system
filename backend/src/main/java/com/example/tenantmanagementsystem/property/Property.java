package com.example.tenantmanagementsystem.property;

import com.example.tenantmanagementsystem.apartment.Apartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @OneToMany
    private Set<Apartment> apartments = new HashSet<>();

    public Property() {
    }

    public Property(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Property(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    public void addApartment(Apartment apartment) {
        this.apartments.add(apartment);
        apartment.setProperty(this);
    }

    public void removeApartment(Apartment apartment) {
        this.apartments.remove(apartment);
        apartment.setProperty(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id) && Objects.equals(name, property.name) && Objects.equals(address, property.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}
