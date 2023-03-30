package com.example.tenantmanagementsystem.property;

import com.example.tenantmanagementsystem.exception.RequestValidationException;
import com.example.tenantmanagementsystem.exception.ResourceNotFoundException;
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
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "property with id [%s] not found".formatted(id)
                ));
    }

    public Property createProperty(PropertyCreateRequest request) {
        Property property = new Property(request);
        // check if address exists to prevent duplicate address
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, PropertyUpdateRequest updateRequest) {
        // get property
        Property property = getProperty(id);
        // update fields, throw exception if no changes were made
        boolean changes = false;
        property.setName(updateRequest.name());

        // check for duplicate address
        property.setAddress(updateRequest.address());

        // if no changes were mad throw exception
        if (!changes) {
            throw new RequestValidationException(
                    "No changes were detected"
            );
        }

        // save
        propertyRepository.save(property);
    }

    public void deletePropertyById(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "property with id [%s] not found".formatted(id)
            );
        }
        propertyRepository.deleteById(id);
    }
}
