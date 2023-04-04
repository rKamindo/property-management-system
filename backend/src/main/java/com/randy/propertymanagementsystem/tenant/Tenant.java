package com.randy.propertymanagementsystem.tenant;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.property.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

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

    private String name;
    @Column(unique = true)
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "tenant")
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}
