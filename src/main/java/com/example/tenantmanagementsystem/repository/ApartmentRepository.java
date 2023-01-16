package com.example.tenantmanagementsystem.repository;

import com.example.tenantmanagementsystem.model.Apartment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

}
