package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.client.ClientService;
import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.RequestValidationException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.exception.ResourceOwnershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final ClientService clientService;

    public List<Property> getAllPropertiesForUser(String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        return propertyRepository.findAllByClient(client);
    }

    public boolean doesPropertyBelongToUser(Property property, String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        return property
                .getClient()
                .getId()
                .equals(client.getId());
    }

    public Property getProperty(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "property with id [%s] not found".formatted(id)
                ));
    }

    public Property createPropertyForUser(PropertyCreateRequest request, String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        // todo check if address exists to prevent duplicate address
        if (propertyRepository.existsPropertyByAddress(request.address())) {
            throw new DuplicateResourceException(
                    "A property with this address already exists"
            );
        }
        Property property = Property.builder()
                .client(client)
                .name(request.name())
                .address(request.address())
                .build();
        client.addProperty(property);
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, UpdatePropertyRequest updateRequest) {
        // get property
        Property property = getProperty(id);
        // update fields, throw exception if no changes were made
        boolean changes = false;
        if (updateRequest.name() != null && updateRequest.name() != property.getName()) {
            property.setName(updateRequest.name());
            changes = true;
        }

        // check for duplicate address
        if (!Objects.equals(updateRequest.address(), property.getAddress())) {
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

    public void deleteProperty(Long id, String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        Property property = getProperty(id);
        if (!doesPropertyBelongToUser(property, userEmail)) {
           throw new ResourceOwnershipException(
                   "property with id [%s] does not belong to user with email '[%s]'"
                           .formatted(id, userEmail)
           );
        }
        // if the property belongs to the user, delete it
        client.removeProperty(property);
        propertyRepository.deleteById(id);
    }
}
