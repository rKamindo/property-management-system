package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.RequestValidationException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.exception.ResourceOwnershipException;
import com.randy.propertymanagementsystem.tenant.Tenant;

import java.util.List;

public interface IPropertyService {
    List<Property> getPropertiesForUser();

    Property getProperty(Long id) throws ResourceNotFoundException;

    Property createPropertyForUser(PropertyCreateRequest request) throws DuplicateResourceException;

    Property updateProperty(Long id, UpdatePropertyRequest updateRequest) throws ResourceNotFoundException, DuplicateResourceException, RequestValidationException;

    void deleteProperty(Long id) throws ResourceNotFoundException, ResourceOwnershipException;

    List<Tenant> getAllTenants(Long propertyId) throws ResourceNotFoundException;

    List<Apartment> getAllApartments(Long propertyId) throws ResourceNotFoundException;
}
