package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.Apartment;
import com.randy.propertymanagementsystem.apartment.ApartmentService;
import com.randy.propertymanagementsystem.apartment.IApartmentService;
import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.client.ClientService;
import com.randy.propertymanagementsystem.exception.*;
import com.randy.propertymanagementsystem.ownership.OwnershipService;
import com.randy.propertymanagementsystem.tenant.Tenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PropertyService implements IPropertyService {
    private final PropertyRepository propertyRepository;
    private final ClientService clientService;
    private final OwnershipService ownershipService;
    private final IPropertyApartmentService propertyApartmentService;
    private final IPropertyTenantService propertyTenantService;

    public List<Property> getPropertiesForUser() {
        Client client = clientService.getCurrentClient();
        return propertyRepository.findAllByClient(client);
    }

    public Property getProperty(Long id) {
        Property property =  propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "property with id [%s] not found".formatted(id)
                ));
        if(!ownershipService.belongsToUser(property)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }
        return property;
    }

    public Property createPropertyForUser(PropertyCreateRequest request) {
        Client client = clientService.getCurrentClient();
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
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, UpdatePropertyRequest updateRequest) {
        // get property
        Property property = getProperty(id);
        // check ownership
        ownershipService.belongsToUser(property);
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

    public void deleteProperty(Long id) {
        Client client = clientService.getCurrentClient();
        Property property = getProperty(id);

        // check ownership
        if(!ownershipService.belongsToUser(property)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }

        client.removeProperty(property);
        propertyRepository.deleteById(id);
    }

    public List<Tenant> getAllTenants(Long propertyId) {
        return propertyTenantService.getTenantsForProperty(
                getProperty(propertyId)
        );
    }

    public List<Apartment> getAllApartments(Long propertyId) {
        return propertyApartmentService.getApartmentsForProperty(
                getProperty(propertyId)
        );
    }
}
