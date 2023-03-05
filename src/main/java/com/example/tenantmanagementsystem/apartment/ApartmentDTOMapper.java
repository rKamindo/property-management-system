package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.tenant.Tenant;
import com.example.tenantmanagementsystem.tenant.TenantDTOMapper;

import java.util.function.Function;

public class ApartmentDTOMapper implements Function<Apartment, ApartmentDTO> {

    private final TenantDTOMapper tenantDTOMapper;

    public ApartmentDTOMapper(TenantDTOMapper tenantDTOMapper) {
        this.tenantDTOMapper = tenantDTOMapper;
    }

    @Override
    public ApartmentDTO apply(Apartment apartment) {
        Tenant tenant = apartment.getTenant();
        if (tenant == null) {
            return new ApartmentDTO(
                    apartment.getId(),
                    apartment.getApartmentNumber(),
                    apartment.getNumberOfRooms(),
                    apartment.getApartmentRentalRate(),
                    null
            );
        } else {
            return new ApartmentDTO(
                    apartment.getId(),
                    apartment.getApartmentNumber(),
                    apartment.getNumberOfRooms(),
                    apartment.getApartmentRentalRate(),
                    tenantDTOMapper.apply(apartment.getTenant())
            );
        }
    }
}
