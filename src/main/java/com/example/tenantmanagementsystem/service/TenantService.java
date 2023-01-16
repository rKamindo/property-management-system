package com.example.tenantmanagementsystem.service;

import com.example.tenantmanagementsystem.dto.TenantDTO;
import com.example.tenantmanagementsystem.exception.ApartmentNotFoundException;
import com.example.tenantmanagementsystem.exception.TenantNotFoundException;
import com.example.tenantmanagementsystem.model.Apartment;
import com.example.tenantmanagementsystem.model.Tenant;
import com.example.tenantmanagementsystem.repository.ApartmentRepository;
import com.example.tenantmanagementsystem.repository.TenantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;

    public TenantService(TenantRepository tenantRepository, ModelMapper modelMapper, ApartmentRepository apartmentRepository) {
        this.tenantRepository = tenantRepository;
        this.modelMapper = modelMapper;
        this.apartmentRepository = apartmentRepository;
    }

    public TenantDTO createTenant(Tenant tenant) {
        tenant.setUuid(UUID.randomUUID());
        tenant.setApartment(getApartmentbyId(tenant.getApartment().getId()));
        return mapTenantToDTO(tenantRepository.save(tenant));
    }

    public List<TenantDTO> findAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        List<TenantDTO> tenantDTOS = tenants.stream()
                .map(tenant -> modelMapper.map(tenant, TenantDTO.class))
                .collect(Collectors.toList());
        return tenantDTOS;
    }

    public TenantDTO updateTenant(Long id, Tenant tenantDetails) {
        Tenant updateTenant = tenantRepository.findById(id)
                .orElseThrow(() ->  new TenantNotFoundException("Tenant by id " + id + " was not found"));
        updateTenant.setUuid(tenantDetails.getUuid());
        updateTenant.setFirstName(tenantDetails.getFirstName());
        updateTenant.setLastName(tenantDetails.getLastName());
        updateTenant.setPhone(tenantDetails.getPhone());
        updateTenant.setEmail(tenantDetails.getEmail());
        updateTenant.setAddress((tenantDetails.getAddress()));

        // update apartment business logic
        if (tenantDetails.getApartment() != null) {
            Long apartmentId = tenantDetails.getApartment().getId();
            updateTenant.setApartment(getApartmentbyId(apartmentId));
        }
        return mapTenantToDTO(tenantRepository.save(updateTenant));
    }

    public TenantDTO findTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() ->  new TenantNotFoundException("Tenant by id " + id + " was not found"));
        TenantDTO tenantDTO = mapTenantToDTO(tenant);
        return tenantDTO;
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }

    private Apartment getApartmentbyId(Long apartmentId) {
        if (!apartmentRepository.existsById(apartmentId)) {
            throw new ApartmentNotFoundException("Apartment by id " + apartmentId + " not found");
        }
        return apartmentRepository.findById(apartmentId).get();
    }

    private TenantDTO mapTenantToDTO(Tenant tenant) {
        TenantDTO tenantDTO = modelMapper.map(tenant, TenantDTO.class);
        return tenantDTO;
    }

    private Tenant mapDTOToTenant(TenantDTO tenantDTO) {
        Tenant tenant = modelMapper.map(tenantDTO, Tenant.class);
        return tenant;
    }

}
