package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.tenant.TenantDTO;

public record ApartmentDTO (
        Long id,
        String apartmentNumber,
        double rent,
        TenantDTO tenantDTO
) {

}