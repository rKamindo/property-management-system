package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.tenant.Tenant;

import java.util.List;

public interface PropertyTenantServiceImpl {
    List<Tenant> getTenantsForProperty(Property property);
}
