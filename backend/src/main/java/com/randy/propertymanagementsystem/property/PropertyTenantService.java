package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.tenant.Tenant;
import com.randy.propertymanagementsystem.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyTenantService implements IPropertyTenantService {
    private final TenantService tenantService;

    @Override
    public List<Tenant> getTenantsForProperty(Property property) {
        return tenantService.getTenantsForProperty(property);
    }
}
