package com.randy.tenantmanagementsystem.tenant;

import com.randy.tenantmanagementsystem.exception.DuplicateResourceException;
import com.randy.tenantmanagementsystem.exception.RequestValidationException;
import com.randy.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Tenant createTenant(TenantCreateRequest createRequest) {
        // check if email exists
        String email = createRequest.email();
        if (tenantRepository.existsTenantByEmail(email)) {
            throw new DuplicateResourceException(
                    "A tenant with this email already exists"
            );
        }

        // create tenant
        Tenant tenant = new Tenant(createRequest);

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
                        "A tenant with this email already exists"
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
                    "No changes were detected"
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
