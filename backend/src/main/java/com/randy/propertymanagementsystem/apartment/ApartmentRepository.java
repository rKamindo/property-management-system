package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findAllByClient(Client client);

    List<Apartment> findAllByProperty(Property property);
}
