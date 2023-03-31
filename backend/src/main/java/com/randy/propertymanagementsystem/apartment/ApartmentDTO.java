package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.tenant.TenantDTO;

public record ApartmentDTO (
        Long id,
        String apartmentNumber,
        int numberOfRooms,
        double rent,
        TenantDTO tenantDTO,
        boolean isAvailable,
        boolean isOccupied
) {

}