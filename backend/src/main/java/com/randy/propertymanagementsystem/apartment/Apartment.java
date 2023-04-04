package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.tenant.Tenant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;


@Entity
@Getter
@Setter
@Builder
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
    private double rent; // todo validate

    // todo ApartmentStatus ENUM?
    @JoinColumn(name = "tenant_id")
    @OneToOne(cascade = CascadeType.PERSIST)
    private Tenant tenant;
    @JoinColumn(name = "property_id")
    @OneToOne
    private Property property;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public void setTenant(Tenant tenant) {
        if (tenant == null) {
            this.tenant = null;
        }
        else {
            this.tenant = tenant;
        }
    }

}