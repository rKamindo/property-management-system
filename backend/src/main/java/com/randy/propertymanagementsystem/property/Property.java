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

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String address;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Apartment> apartments = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Tenant> tenants = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public void removeApartment(Apartment apartment) {
        apartments.remove(apartment);
        apartment.setTenant(null);
    }
}
