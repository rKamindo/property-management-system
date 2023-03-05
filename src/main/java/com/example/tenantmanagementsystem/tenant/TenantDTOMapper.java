package com.example.tenantmanagementsystem.tenant;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TenantDTOMapper implements Function<Tenant, TenantDTO> {
    @Override
    public TenantDTO apply(Tenant tenant) {
        return new TenantDTO(
                tenant.getId(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getGender(),
                tenant.getApartment().getApartmentNumber()
        );
    }
}
