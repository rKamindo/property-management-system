package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.exception.DuplicateResourceException;
import com.randy.propertymanagementsystem.exception.ResourceNotFoundException;
import com.randy.propertymanagementsystem.tenant.Tenant;
import com.randy.propertymanagementsystem.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TenantService tenantService;

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment getApartment(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "apartment with id [%s] not found".formatted(id)
                ));
    }

    public Apartment addApartment(ApartmentCreateRequest request) {
        // check if duplicate apartment number exists
        Apartment apartment = new Apartment(request);
        
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Long id, ApartmentUpdateRequest updateRequest) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "apartment with id [%s] not found".formatted(id)
                ));

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

    public Apartment putTenantInApartment(Long apartmentId, Long tenantId) {
        Apartment apartment = getApartment(apartmentId);
        Tenant tenant = tenantService.getTenant(tenantId);
        apartment.setTenant(tenant);
        return apartmentRepository.save(apartment);
    }

    public Apartment removeTenantFromApartment(Long apartmentId) {
        Apartment apartment = getApartment(apartmentId);
        apartment.setTenant(null);
        return apartmentRepository.save(apartment);
    }
}
