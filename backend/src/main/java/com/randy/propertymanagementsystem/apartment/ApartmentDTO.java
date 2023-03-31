package com.randy.tenantmanagementsystem.apartment;

import com.randy.tenantmanagementsystem.tenant.TenantDTO;

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