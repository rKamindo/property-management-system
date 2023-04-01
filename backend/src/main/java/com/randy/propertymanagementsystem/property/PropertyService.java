package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.RequestValidationException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getProperty(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "property with id [%s] not found".formatted(id)
                ));
    }

    public boolean existsById(Long propertyId) {
        return propertyRepository.existsById(propertyId);
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

    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "property with id [%s] not found".formatted(id)
            );
        }
        propertyRepository.deleteById(id);
    }
}
