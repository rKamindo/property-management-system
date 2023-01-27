package com.example.tenantmanagementsystem.repository;

import com.example.tenantmanagementsystem.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Boolean existsByEmail(String email);

    Optional<Tenant> findByEmail(String email);
}
