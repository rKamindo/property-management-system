package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.tenant.TenantDTOMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ApartmentDTOMapper implements Function<Apartment, ApartmentDTO> {

    private final TenantDTOMapper tenantDTOMapper;

    public ApartmentDTOMapper(TenantDTOMapper tenantDTOMapper) {
        this.tenantDTOMapper = tenantDTOMapper;
    }

    @Override
    public ApartmentDTO apply(Apartment apartment) {

            return new ApartmentDTO(
                    apartment.getId(),
                    apartment.getApartmentNumber(),
                    apartment.getRent(),
                    apartment.getTenant() == null ?
                            null : tenantDTOMapper.apply(apartment.getTenant())
            );
        }
}
