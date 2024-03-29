package com.randy.propertymanagementsystem.tenant;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsTenantByEmail(String email);

    List<Tenant> findAllByClient(Client client);

    List<Tenant> findAllByProperty(Property property
    );
}
