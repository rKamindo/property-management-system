package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.apartment.Apartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Tenant {

    @Id
    @SequenceGenerator(
            name = "tenant_id_seq",
            sequenceName = "tenant_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
    generator = "tenant_id_seq")
    private Long id;
    @NotBlank

    private String name;
    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    @Pattern(regexp = "\\d+", message = "Phone number should contain only digits")
    @Size(min = 3, max = 14, message = "Phone number should between 3 and 14 digits")
    @Column(nullable = false)
    private String phone;
    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(mappedBy = "tenant")
    private Apartment apartment;

    public Tenant() {
    }

    public Tenant(Long id, String name, String email, String phone, Gender gender, Apartment apartment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.apartment = apartment;
    }

    public Tenant(String name, String email, String phone, Gender gender, Apartment apartment) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.apartment = apartment;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(id, tenant.id) && Objects.equals(name, tenant.name) && Objects.equals(email, tenant.email) && Objects.equals(phone, tenant.phone) && gender == tenant.gender && Objects.equals(apartment, tenant.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, gender, apartment);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", apartment=" + apartment +
                '}';
    }
}
