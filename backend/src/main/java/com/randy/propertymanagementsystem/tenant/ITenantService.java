package com.randy.propertymanagementsystem.tenant;

import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.RequestValidationException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.property.Property;

import java.util.List;

public interface ITenantService {
    List<Tenant> getTenantsForUser();

    Tenant getTenant(Long tenantId) throws ResourceNotFoundException;

    Tenant createTenantForUser(CreateTenantRequest request) throws DuplicateResourceException;

    Tenant updateTenant(Long tenantId, TenantUpdateRequest updateRequest)
            throws ResourceNotFoundException, DuplicateResourceException, RequestValidationException;

    void deleteTenant(Long tenantId) throws ResourceNotFoundException;

    List<Tenant> getTenantsForProperty(Property property);

}
