package com.example.tenantmanagementsystem.repository;

import com.example.tenantmanagementsystem.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Boolean existsByEmail(String email);
}
