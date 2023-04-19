package com.randy.propertymanagementsystem.apartment;

public interface IApartmentTenantService {

    Apartment addTenantToApartment(Long tenantId, Long apartmentId);

    Apartment removeTenantFromApartment(Long apartmentId);
}
