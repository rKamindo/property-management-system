package com.randy.tenantmanagementsystem.property;

import com.randy.tenantmanagementsystem.exception.DuplicateResourceException;
import com.randy.tenantmanagementsystem.exception.RequestValidationException;
import com.randy.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (updateRequest.name() != null && updateRequest.name() != property.getName()) {
            property.setName(updateRequest.name());
            changes = true;
        }

        // check for duplicate address
        if (updateRequest.address() != null && updateRequest.address() != property.getAddress()) {
            if (propertyRepository.existsPropertyByAddress(updateRequest.address()))
                throw new DuplicateResourceException(
                        "A property with this address already exists"
                );
            property.setAddress(updateRequest.address());
            changes = true;
        }

        // if no changes were made throw exception
        if (!changes) {
            throw new RequestValidationException(
                    "No changes were detected"
            );
        }

        // save
        return propertyRepository.save(property);
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
