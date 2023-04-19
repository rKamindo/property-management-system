package com.randy.propertymanagementsystem.tenant;

import com.randy.propertymanagementsystem.exception.UserNotAuthorizedException;
import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.client.ClientService;
import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.RequestValidationException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.ownership.OwnershipService;
import com.randy.propertymanagementsystem.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService implements ITenantService {
    private final TenantRepository tenantRepository;
    private final OwnershipService ownershipService;
    private final ClientService clientService;

    public List<Tenant> getTenantsForUser() {
        return tenantRepository.findAllByClient(
                clientService.getCurrentClient()
        );
    }

    public Tenant getTenant(Long tenantId){
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(tenantId)
                ));
        if(!ownershipService.belongsToUser(tenant)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }
        return tenant;
    }

    public Tenant createTenantForUser(CreateTenantRequest request) {
        Client client = clientService.getCurrentClient();
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
                .gender(request.gender())
                .build();
        client.addTenant(tenant);
        return tenantRepository.save(tenant);
    }

    public Tenant updateTenant(Long tenantId, TenantUpdateRequest updateRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(tenantId)
                ));

        if(!ownershipService.belongsToUser(tenant)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(tenant.getName())) {
            tenant.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(tenant.getEmail())) {
            if (tenantRepository.existsTenantByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "a tenant with this email already exists"
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
                    "no changes were detected"
            );

        return tenantRepository.save(tenant);
    }

    public void deleteTenant(Long tenantId) {
        Tenant tenant = getTenant(tenantId);
        if(!ownershipService.belongsToUser(tenant)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }
        tenantRepository.deleteById(tenantId);
    }

    public List<Tenant> getTenantsForProperty(Property property) {
        return tenantRepository.findAllByProperty(property);
    }
}
