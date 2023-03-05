package com.example.tenantmanagementsystem.apartment;

import org.springframework.stereotype.Service;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentDTOMapper apartmentDTOMapper;

    public ApartmentService(ApartmentRepository apartmentRepository, ApartmentDTOMapper apartmentDTOMapper) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentDTOMapper = apartmentDTOMapper;
    }

    public List<ApartmentDTO> getAllApartments() {
        return apartmentRepository.findAll()
                .stream()
                .map(apartmentDTOMapper)
                .collect(Collectors.toList());
    }

    public ApartmentDTO getApartment(Long id) {
        return apartmentRepository.findById(id)
                .map(apartmentDTOMapper)
                .orElseThrow(() -> new ResolutionException(
                        "apartment with id [%s] not found".formatted(id)
                ));
    }

    public void addApartment(Apartment apartment) {
        // check if duplicate apartment number exists
        apartmentRepository.save(apartment);
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
}
