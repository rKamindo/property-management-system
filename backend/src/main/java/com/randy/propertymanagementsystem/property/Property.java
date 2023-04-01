package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.Apartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @Setter(AccessLevel.NONE)
    @OneToMany
    private Set<Apartment> apartments = new HashSet<>();

    @Setter(AccessLevel.NONE)
    private Long clientId;

    public Property(PropertyCreateRequest request) {
        this.name = request.name();
        this.address = request.address();
    }

    public void setClientId(Long clientId) {
        if (clientId == null)
            throw new IllegalArgumentException("clientId cannot be null");
        this.clientId = clientId;
    }

}
