package com.randy.propertymanagementsystem.tenant;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.client.Client;
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

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "tenant")
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}
