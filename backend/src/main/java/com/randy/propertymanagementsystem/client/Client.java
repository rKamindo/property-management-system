package com.randy.propertymanagementsystem.client;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.tenant.Tenant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "client_id_seq",
            sequenceName = "client_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_id_seq")

    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "client")
    private Set<Property> properties = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Apartment> apartments = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Tenant> tenants = new HashSet<>();

    public void addProperty(Property property) {
        properties.add(property);
        property.setClient(this);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        property.setClient(null);
    }

    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
        apartment.setClient(this);
    }

    public void removeApartment(Apartment apartment) {
        apartments.remove(apartment);
        apartment.setClient(null);
    }

    public void addTenant(Tenant tenant) {
        tenants.add(tenant);
        tenant.setClient(this);
    }

    public void removeTenant(Tenant tenant) {
        tenants.remove(tenant);
        tenant.setClient(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
