package com.example.tenantmanagementsystem.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsTenantByEmail(String email);
}
