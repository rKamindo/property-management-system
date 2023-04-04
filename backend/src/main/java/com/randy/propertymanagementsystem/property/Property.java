package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.tenant.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @NotBlank
    @Column(unique = true, nullable = false)
    private String address;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Apartment> apartments = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Tenant> tenants = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
        apartment.setProperty(this);
    }

    public void removeApartment(Apartment apartment) {
        apartments.remove(apartment);
        apartment.setTenant(null);
    }
}
