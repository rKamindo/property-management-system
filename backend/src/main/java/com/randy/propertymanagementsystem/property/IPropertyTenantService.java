package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.tenant.Tenant;

import java.util.List;

public interface IPropertyTenantService {
    List<Tenant> getTenantsForProperty(Property property);
}
