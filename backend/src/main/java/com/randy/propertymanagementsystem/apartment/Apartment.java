package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.tenant.Tenant;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private int bedrooms;
    @Column(nullable = false)
    private int bathrooms;
    @Column(nullable = false)
    private double rent;
    @Column(nullable = false)

    private boolean isAvailable;
    @Column(nullable = false)
    private boolean isOccupied;
    @Setter(AccessLevel.NONE)
    @JoinColumn(name = "tenant_id")
    @OneToOne(cascade = CascadeType.PERSIST)
    private Tenant tenant;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    @Setter(AccessLevel.NONE)
    @Column(name = "client_id")
    private Long clientId;
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
}