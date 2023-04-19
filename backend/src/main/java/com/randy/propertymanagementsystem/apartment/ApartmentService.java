package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.client.Client;
import com.randy.propertymanagementsystem.client.ClientService;
import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.exception.UserNotAuthorizedException;
import com.randy.propertymanagementsystem.ownership.OwnershipService;
import com.randy.propertymanagementsystem.property.Property;
import com.randy.propertymanagementsystem.tenant.ITenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService implements IApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final OwnershipService ownershipService;
    private final ClientService clientService;

    public List<Apartment> getApartmentsForUser() {
        Client client = clientService.getCurrentClient();
        return apartmentRepository.findAllByClient(client);
    }

    public Apartment getApartment(Long id) {
        Apartment apartment =  apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "apartment with id [%s] not found".formatted(id)
                ));
        if (!ownershipService.belongsToUser(apartment)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }
        return apartment;
    }

    public Apartment createApartmentForUser(
            CreateApartmentRequest request,
            Property property) {
        // todo check if duplicate apartment number exists

        // get user who made request
        Client client = clientService.getCurrentClient();

        // build apartment
        Apartment apartment = Apartment.builder()
                .client(client)
                .property(property)
                .apartmentNumber(request.apartmentNumber())
                .rent(request.rent())
                .build();

        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Long id, UpdateApartmentRequest updateRequest) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "apartment with id [%s] not found".formatted(id)
                ));

        ownershipService.belongsToUser(apartment);

        // update apartment number
        if (updateRequest.apartmentNumber().equals(apartment.getApartmentNumber())) {
            throw new DuplicateResourceException(
                    "apartment number already taken"
            );
        }

        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(Long id) {
        Apartment apartment = getApartment(id);
        if (!ownershipService.belongsToUser(apartment)) {
            throw new UserNotAuthorizedException(
                    "user does not have permission to access this resource");
        }
        apartmentRepository.deleteById(id);
    }

    public List<Apartment> getApartmentsForProperty(Property property) {
        return apartmentRepository.findAllByProperty(property);
    }
}
