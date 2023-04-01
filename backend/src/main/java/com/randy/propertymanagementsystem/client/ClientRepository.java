package com.randy.propertymanagementsystem.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository {
    Optional<Client> findByEmail(String email);
}
