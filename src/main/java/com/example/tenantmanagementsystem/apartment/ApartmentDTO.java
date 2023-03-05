package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.tenant.TenantDTO;

public record ApartmentDTO (
        Long id,
        String apartmentNumber,
        int numberOfRooms,
        double apartmentRentalRate,
        TenantDTO tenantDTO
) {

}