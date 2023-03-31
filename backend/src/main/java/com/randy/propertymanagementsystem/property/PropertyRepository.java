package com.randy.propertymanagementsystem.property;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    boolean existsPropertyByAddress(String address);
}
