package com.example.tenantmanagementsystem.service;

import com.example.tenantmanagementsystem.exception.ApartmentNotFoundException;
import com.example.tenantmanagementsystem.model.Apartment;
import com.example.tenantmanagementsystem.repository.ApartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
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

    public Apartment findApartmentById(Long id) {
        return apartmentRepository.findApartmentById(id)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment by id " + id + " was not found"));
    }

    @Transactional
    public void deleteApartment(Long id) {
        apartmentRepository.deleteApartmentById(id);
    }
}
