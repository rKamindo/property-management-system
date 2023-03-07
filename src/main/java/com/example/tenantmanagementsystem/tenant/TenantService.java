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
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));
        return tenantDTOMapper.apply(tenant);
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

    public TenantDTO updateTenant(Long id, TenantUpdateRequest updateRequest) {

        // check if it exists
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                        ));

        // update name
        if (updateRequest.name() != null && !updateRequest.name().equals(tenant.getName())) {
            tenant.setName(updateRequest.name());
        }

        // update email
        if (updateRequest.email() != null && !updateRequest.email().equals(tenant.getEmail())) {
            if (tenantRepository.existsTenantByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            tenant.setEmail(updateRequest.email());
        }

        // update phone
        if (updateRequest.phone() != null && !updateRequest.phone().equals(tenant.getPhone())) {
            tenant.setPhone(updateRequest.phone());
        }

        // update apartment using apartmentNumber?

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
