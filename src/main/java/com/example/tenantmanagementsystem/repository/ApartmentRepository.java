package com.example.tenantmanagementsystem.repository;

import com.example.tenantmanagementsystem.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

}
