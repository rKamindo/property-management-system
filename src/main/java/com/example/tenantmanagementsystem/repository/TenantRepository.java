package com.example.tenantmanagementsystem.repository;

import com.example.tenantmanagementsystem.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    void deleteTenantById(Long id);

    Optional<Tenant> findTenantById(Long id);
}
