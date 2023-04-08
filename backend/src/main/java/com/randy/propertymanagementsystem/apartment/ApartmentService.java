package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.client.ClientService;
import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.property.PropertyService;
import com.randy.propertymanagementsystem.tenant.Tenant;
import com.randy.propertymanagementsystem.tenant.TenantRepository;
import com.randy.propertymanagementsystem.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ClientService clientService;
    private final PropertyService propertyService;
    private final TenantService tenantService;

    public Apartment getApartment(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "apartment with id [%s] not found".formatted(id)
                ));
    }

    public Apartment createApartmentForUser(
            CreateApartmentRequest request,
            String userEmail) {
        // todo check if duplicate apartment number exists

        // get user who made request
        Client client = clientService.findByEmail(userEmail);

        // get property associated with property id
        Property property = propertyService.getProperty(request.propertyId());

        // build apartment
        Apartment apartment = Apartment.builder()
                .client(client)
                .property(property)
                .apartmentNumber(request.apartmentNumber())
                .rent(request.rent())
                .build();

        // add apartment to the list of apartments
        property.getApartments().add(apartment);
        
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Long id, ApartmentUpdateRequest updateRequest) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "apartment with id [%s] not found".formatted(id)
                ));

        // update apartment number
        if (updateRequest.apartmentNumber() == apartment.getApartmentNumber()) {
            throw new DuplicateResourceException(
                    "apartment number already taken"
            );
        }

        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }

    public List<Apartment> getApartmentsForUser(String userEmail) {
        Client client = clientService.findByEmail(userEmail);
        return apartmentRepository.findAllByClient(client);
    }

    public List<Apartment> getApartmentsForProperty(Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        return apartmentRepository.findAllByProperty(property);
    }

    public void addTenantToApartment(Long tenantId, Long apartmentId) {
        Tenant tenant = tenantService.getTenant(tenantId);
        Apartment apartment = getApartment(apartmentId);
        apartment.setTenant(tenant);
        tenant.setApartment(apartment);
        apartmentRepository.save(apartment);
    }
    public void removeTenantFromApartment(Long apartmentId) {
        Apartment apartment = getApartment(apartmentId);
        apartment.setTenant(null);
        apartmentRepository.save(apartment);
    }
}
