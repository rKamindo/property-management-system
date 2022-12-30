package com.example.tenantmanagementsystem.service;

import com.example.tenantmanagementsystem.exception.TenantNotFoundException;
import com.example.tenantmanagementsystem.model.Tenant;
import com.example.tenantmanagementsystem.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant addTenant(Tenant tenant) {
        tenant.setTenantCode(UUID.randomUUID().toString());
        return tenantRepository.save(tenant);
    }

    public List<Tenant> findAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant updateTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant findTenantFindById(Long id) {
        return tenantRepository.findTenantById(id)
                .orElseThrow(() ->  new TenantNotFoundException("Tenant by id " + id + " was not found"));
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteTenantById(id);
    }


}
