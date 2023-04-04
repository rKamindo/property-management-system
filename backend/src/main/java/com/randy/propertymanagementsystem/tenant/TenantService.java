package com.randy.propertymanagementsystem.tenant;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.client.ClientService;
import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.RequestValidationException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.property.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final ClientService clientService;
    private final PropertyService propertyService;

    public List<Tenant> getTenantsForUser(String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        return tenantRepository.findAllByClient(client);
    }

    public boolean doesTenantBelongToUser(Tenant tenant, String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        return tenant.getClient().equals(client);
    }

    public Tenant getTenant(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(tenantId)
                ));
    }

    public Tenant createTenantForUser(CreateTenantRequest request, String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        if (tenantRepository.existsTenantByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "A tenant with this email already exists"
            );
        }
        Tenant tenant = Tenant.builder()
                .client(client)
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .build();
        client.addTenant(tenant);
        return tenantRepository.save(tenant);
    }

    public Tenant updateTenant(Long tenantId, TenantUpdateRequest updateRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(tenantId)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(tenant.getName())) {
            tenant.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(tenant.getEmail())) {
            if (tenantRepository.existsTenantByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "A tenant with this email already exists"
                );
            }
            tenant.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.phone() != null && !updateRequest.phone().equals(tenant.getPhone())) {
            tenant.setPhone(updateRequest.phone());
            changes = true;
        }

        if (!changes)
            throw new RequestValidationException(
                    "No changes were detected"
            );

        return tenantRepository.save(tenant);
    }

    public void deleteTenantById(Long tenantId) {
        if (!tenantRepository.existsById(tenantId)) {
            throw new ResourceNotFoundException(
                    "tenant with id [%s] not found".formatted(tenantId)
            );
        }
        tenantRepository.deleteById(tenantId);
    }

    public List<Tenant> getTenantsForProperty(Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        return tenantRepository.findAllByProperty(property);
    }
}
