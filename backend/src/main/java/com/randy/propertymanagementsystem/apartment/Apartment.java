package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.ownership.IClientResource;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.tenant.Tenant;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apartment implements IClientResource {

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
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}