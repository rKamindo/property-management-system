package com.example.tenantmanagementsystem.property;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getProperty(Long id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(PropertyCreateRequest request) {
        Property property = new Property(request)
    }
}
