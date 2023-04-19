package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.tenant.ITenantService;
import com.randy.propertymanagementsystem.tenant.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentTenantService  implements IApartmentTenantService{
    private final ApartmentRepository apartmentRepository;
    private final IApartmentService apartmentService;
    private final ITenantService tenantService;

    public Apartment addTenantToApartment(Long tenantId, Long apartmentId) {
        Tenant tenant = tenantService.getTenant(tenantId);
        Apartment apartment = apartmentService.getApartment(apartmentId);
        Property property = apartment.getProperty();
        tenant.setProperty(property);
        apartment.setTenant(tenant);
        return apartmentRepository.save(apartment);
    }
    public Apartment removeTenantFromApartment(Long apartmentId) {
        Apartment apartment = apartmentService.getApartment(apartmentId);
        Tenant tenant = apartment.getTenant();
        tenant.setProperty(null);
        apartment.setTenant(null);
        return apartmentRepository.save(apartment);
    }
}
