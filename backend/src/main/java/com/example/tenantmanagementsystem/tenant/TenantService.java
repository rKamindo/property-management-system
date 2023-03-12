package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.exception.DuplicateResourceException;
import com.example.tenantmanagementsystem.exception.RequestValidationException;
import com.example.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));
        return tenant;
    }

    public Tenant addTenant(TenantCreateRequest tenantCreateRequest) {
        // check if email exists
        String email = tenantCreateRequest.email();
        if (tenantRepository.existsTenantByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        // create tenant
        Tenant tenant = new Tenant(
                tenantCreateRequest.name(),
                tenantCreateRequest.email(),
                tenantCreateRequest.phone(),
                tenantCreateRequest.gender(),
                null
        );

        // add
        return tenantRepository.save(tenant);
    }

    public Tenant updateTenant(Long id, TenantUpdateRequest updateRequest) {

        // check if it exists
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));

        boolean changes = false;

        // update name
        if (updateRequest.name() != null && !updateRequest.name().equals(tenant.getName())) {
            tenant.setName(updateRequest.name());
            changes = true;
        }

        // update email
        if (updateRequest.email() != null && !updateRequest.email().equals(tenant.getEmail())) {
            // check duplicate email
            if (tenantRepository.existsTenantByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            tenant.setEmail(updateRequest.email());
            changes = true;
        }

        // update phone
        if (updateRequest.phone() != null && !updateRequest.phone().equals(tenant.getPhone())) {
            // check for duplicate phone
            tenant.setPhone(updateRequest.phone());
            changes = true;
        }

        if (!changes)
            throw new RequestValidationException(
                    "no data changes found"
            );

        return tenantRepository.save(tenant);
    }

    public void deleteTenantById(Long id) {
        if (!tenantRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "tenant with id [%s] not found".formatted(id)
            );
        }
        tenantRepository.deleteById(id);
    }
}
