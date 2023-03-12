package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.exception.ResourceNotFoundException;
import com.example.tenantmanagementsystem.tenant.Tenant;
import com.example.tenantmanagementsystem.tenant.TenantService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final TenantService tenantService;
    private final ApartmentDTOMapper apartmentDTOMapper;

    public ApartmentService(ApartmentRepository apartmentRepository, ApartmentDTOMapper apartmentDTOMapper, TenantService tenantService) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentDTOMapper = apartmentDTOMapper;
        this.tenantService = tenantService;
    }

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
        Apartment apartment = new Apartment(
                request.apartmentNumber(),
                request.numberOfRooms(),
                request.rent(),
                null,
                request.isAvailable(),
                request.isOccupied()
        );
        
        return apartmentRepository.save(apartment);
    }

    public void updateApartment(Long id, Apartment updateRequest) {
        // check if apartment exists

        // check if apartment number is duplicate

        // update apartment's tenant

        // update apartment

    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }

    public Apartment setTenantForApartment(Long apartmentId, Long tenantId) {
        Apartment apartment = getApartment(apartmentId);
        Tenant tenant = tenantService.getTenant(tenantId);
        apartment.setTenant(tenant);
        return apartmentRepository.save(apartment);
    }
}
