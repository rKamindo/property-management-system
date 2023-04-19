package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.property.Property;

import java.util.List;

public interface IApartmentService {
    Apartment getApartment(Long id);

    Apartment createApartmentForUser(CreateApartmentRequest request, Property property);

    Apartment updateApartment(Long id, UpdateApartmentRequest updateRequest);

    void deleteApartment(Long id);

    List<Apartment> getApartmentsForUser();

    List<Apartment> getApartmentsForProperty(Property property);
}
