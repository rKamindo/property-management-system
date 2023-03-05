package com.example.tenantmanagementsystem.apartment;

import com.example.tenantmanagementsystem.exception.ApartmentNotFoundException;
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

    public List<ApartmentDTO> getAllApartments() {
        List<Apartment> apartments = apartmentRepository.findAll();
        List<ApartmentDTO> apartmentDTOs = apartments.stream()
                .map(apartment -> mapApartmentToDTO(apartment))
                .collect(Collectors.toList());
        return apartmentDTOs;
    }

    public ApartmentDTO findApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment by id " + id + " was not found"));
        ApartmentDTO apartmentDTO = mapApartmentToDTO(apartment);
        return apartmentDTO;
    }

    public ApartmentDTO addApartment(Apartment apartment) {
        return mapApartmentToDTO(apartmentRepository.save(apartment));
    }

    public ApartmentDTO updateApartment(Long id, Apartment apartmentDetails) {
        Apartment updateApartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment by id " + id + " was not found"));
        updateApartment.setApartmentNumber(apartmentDetails.getApartmentNumber());
        updateApartment.setNumberOfRooms(apartmentDetails.getNumberOfRooms());
        updateApartment.setAddress(apartmentDetails.getAddress());
        updateApartment.setRentalRate(apartmentDetails.getRentalRate());
        updateApartment.setTenants(apartmentDetails.getTenants());
        ApartmentDTO apartmentDTO = mapApartmentToDTO(apartmentRepository.save(updateApartment));
        return apartmentDTO;
    }

    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }

    private ApartmentDTO mapApartmentToDTO(Apartment apartment) {
        ApartmentDTO apartmentDTO = modelMapper.map(apartment, ApartmentDTO.class);

        if (apartment.getTenants() != null) {
            // map List<Tenant> to List<TenantLightDTO>
            List<TenantLightDTO> tenantLightDTOS = apartment.getTenants()
                    .stream()
                    .map(tenant -> modelMapper.map(tenant, TenantLightDTO.class))
                    .collect(Collectors.toList());
            apartmentDTO.setTenants(tenantLightDTOS);
        }
        return apartmentDTO;
    }
}
