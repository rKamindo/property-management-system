package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.exception.ApartmentNotFoundException;
import com.example.tenantmanagementsystem.exception.TenantNotFoundException;
import com.example.tenantmanagementsystem.apartment.Apartment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;


    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<TenantDTO> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(TenantDTOMapper)
                .collect(Collectors.toList());
    }

    public TenantDTO getTenant(Long id) {
        return tenantRepository.findById(id)
                .map(TenantDTOMapper)
                .orElseThrow(() -> new TenantNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));
    }

    public void addTenant(Tenant tenant) {
        // check if email exists
        String email = tenant.getEmail();
        if (tenantRepository.existsTenantByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        // add
        tenantRepository.save(tenant);
    }

    public void updateTenant(Long id, Tenant updateRequest) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                        ));

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(tenant.getEmail())) {
            if (tenantRepository.existsTenantByEmail(updateRequest.getEmail())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            tenant.setEmail(updateRequest.getEmail());

            tenantRepository.save(tenant);
        }



    }



    public void deleteTenantById(Long id) {
        if (!tenantRepository.existsById(id)) {
            throw new TenantNotFoundException(
                    "tenant with id [%s] not found".formatted(id)
            );
        }
        tenantRepository.deleteById(id);
    }
}
