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

    public TenantService(TenantRepository tenantRepository, ApartmentRepository apartmentRepository, ModelMapper modelMapper) {
        this.tenantRepository = tenantRepository;
        this.modelMapper = modelMapper;
        this.apartmentRepository = apartmentRepository;
    }

    public TenantDTO addTenant(Tenant tenant) {
        tenant.setUuid(UUID.randomUUID());
        if (tenant.getApartment() != null) {
            tenant.setApartment(getApartmentById(tenant.getApartment().getId()));
        }
        TenantDTO tenantDTO = mapTenantToDTO(tenantRepository.save(tenant));
        return tenantDTO;
    }

    public List<TenantDTO> getAllTenants() {
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
            updateTenant.setApartment(getApartmentById(apartmentId));
        }

        TenantDTO tenantDTO = mapTenantToDTO(tenantRepository.save(updateTenant));
        return tenantDTO;
    }

    public TenantDTO getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() ->  new TenantNotFoundException("Tenant by id " + id + " was not found"));
        TenantDTO tenantDTO = mapTenantToDTO(tenant);
        return tenantDTO;
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }

    private Apartment getApartmentById(Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment by id " + apartmentId + " was not found"));
        return apartment;
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
