package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.apartment.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

}
