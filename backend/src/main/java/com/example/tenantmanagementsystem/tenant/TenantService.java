package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.apartment.Apartment;
import com.example.tenantmanagementsystem.apartment.ApartmentService;
import com.example.tenantmanagementsystem.exception.DuplicateResourceException;
import com.example.tenantmanagementsystem.exception.RequestValidationException;
import com.example.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final TenantDTOMapper tenantDTOMapper;
    private final ApartmentService apartmentService;


    public TenantService(TenantRepository tenantRepository, TenantDTOMapper tenantDTOMapper, ApartmentService apartmentService) {
        this.tenantRepository = tenantRepository;
        this.tenantDTOMapper = tenantDTOMapper;
        this.apartmentService = apartmentService;
    }

    public List<TenantDTO> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(tenantDTOMapper)
                .collect(Collectors.toList());
    }

    public TenantDTO getTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));
        return tenantDTOMapper.apply(tenant);
    }

    public TenantDTO addTenant(TenantCreateRequest tenantCreateRequest) {
        // check if email exists
        String email = tenantCreateRequest.email();
        if (tenantRepository.existsTenantByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        // create tenant
        Tenant tenant = new Tenant(
                tenantCreateRequest.name(),
                tenantCreateRequest.email(),
                tenantCreateRequest.phone(),
                tenantCreateRequest.gender(),
                null
        );

        // set apartment
        Long apartmentId = tenantCreateRequest.apartmentId();
        setApartmentForTenant(tenant, apartmentId);
        // add
        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantDTOMapper.apply(savedTenant);
    }

    public TenantDTO updateTenant(Long id, TenantUpdateRequest updateRequest) {

        // check if it exists
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "tenant with id [%s] not found".formatted(id)
                ));

        boolean changes = false;

        // update name
        if (updateRequest.name() != null && !updateRequest.name().equals(tenant.getName())) {
            tenant.setName(updateRequest.name());
            changes = true;
        }

        // update email
        if (updateRequest.email() != null && !updateRequest.email().equals(tenant.getEmail())) {
            if (tenantRepository.existsTenantByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            tenant.setEmail(updateRequest.email());
            changes = true;
        }

        // update phone
        if (updateRequest.phone() != null && !updateRequest.phone().equals(tenant.getPhone())) {
            tenant.setPhone(updateRequest.phone());
            changes = true;
        }

        // update apartment using apartmentId
        Long apartmentId = updateRequest.apartmentId();
        if (apartmentId != null &&
                !updateRequest.apartmentId().equals(tenant.getApartment().getId())) {
            setApartmentForTenant(tenant, apartmentId);
            changes = true;
        }

        if (!changes)
            throw new RequestValidationException(
                    "no data changes found"
            );
        // update existing tenant
        Tenant updatedTenant = tenantRepository.save(tenant);
        return tenantDTOMapper.apply(updatedTenant);
    }

    public void deleteTenantById(Long id) {
        if (!tenantRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "tenant with id [%s] not found".formatted(id)
            );
        }
        tenantRepository.deleteById(id);
    }

    public void setApartmentForTenant(Tenant tenant, Long apartmentId) {
        if (apartmentId != null && apartmentService.existsApartmentById(apartmentId)) {
            Apartment apartment = apartmentService.getApartmentById(apartmentId);
            tenant.setApartment(apartment);
        }
    }
}
