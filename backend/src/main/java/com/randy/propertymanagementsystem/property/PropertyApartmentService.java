package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.apartment.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyApartmentService implements IPropertyApartmentService {
    private final ApartmentService apartmentService;

    @Override
    public List<Apartment> getApartmentsForProperty(Property property) {
        return apartmentService.getApartmentsForProperty(property);
    }
}
