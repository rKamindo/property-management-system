package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.exception.DuplicateResourceException;
import com.example.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final TenantDTOMapper tenantDTOMapper;


    public TenantService(TenantRepository tenantRepository, TenantDTOMapper tenantDTOMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantDTOMapper = tenantDTOMapper;
    }

    public List<TenantDTO> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(tenantDTOMapper)
                .collect(Collectors.toList());
    }

    public TenantDTO getTenant(Long id) {
        return tenantRepository.findById(id)
                .map(tenantDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));
    }

    public TenantDTO addTenant(Tenant tenant) {
        // check if email exists
        String email = tenant.getEmail();
        if (tenantRepository.existsTenantByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        // add
        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantDTOMapper.apply(savedTenant);
    }

    public TenantDTO updateTenant(Long id, Tenant updateRequest) {
        // todo - use a TenantUpdateRequest record that only has updatable fields

        // check if it exists
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                        ));
        // update name

        // update email
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(tenant.getEmail())) {
            if (tenantRepository.existsTenantByEmail(updateRequest.getEmail())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            tenant.setEmail(updateRequest.getEmail());
        }

        // update phone

        // update apartment using apartmentNumber


        // update existing tenant
        Tenant updatedTenant = tenantRepository.save(tenant);
        return tenantDTOMapper.apply(updatedTenant);
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
