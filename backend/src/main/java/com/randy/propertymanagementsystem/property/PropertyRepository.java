package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    boolean existsPropertyByAddress(String address);

    List<Property> findAllByClient(Client client);
}
