package com.example.tenantmanagementsystem.service;

import com.example.tenantmanagementsystem.dto.ApartmentDTO;
import com.example.tenantmanagementsystem.dto.TenantLightDTO;
import com.example.tenantmanagementsystem.exception.ApartmentNotFoundException;
import com.example.tenantmanagementsystem.model.Apartment;
import com.example.tenantmanagementsystem.repository.ApartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;

    public ApartmentService(ApartmentRepository apartmentRepository, ModelMapper modelMapper) {
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
    }

    public Apartment addApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public List<Apartment> findAllApartments() {
        return apartmentRepository.findAll();
    }

    public Apartment updateApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public ApartmentDTO findApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment by id " + id + " was not found"));
        ApartmentDTO apartmentDTO = mapApartmentToDTO(apartment);
        return apartmentDTO;
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }

    private ApartmentDTO mapApartmentToDTO(Apartment apartment) {
        ApartmentDTO apartmentDTO = modelMapper.map(apartment, ApartmentDTO.class);

        // map List<Tenant> to List<TenantLightDTO>
        List<TenantLightDTO> tenantLightDTOS = apartment.getTenants()
                .stream()
                .map(tenant -> modelMapper.map(tenant, TenantLightDTO.class))
                .collect(Collectors.toList());
        apartmentDTO.setTenants(tenantLightDTOS);
        return apartmentDTO;
    }
}
