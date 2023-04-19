package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.Apartment;

import java.util.List;

public interface IPropertyApartmentService {
    List<Apartment> getApartmentsForProperty(Property property);
}
