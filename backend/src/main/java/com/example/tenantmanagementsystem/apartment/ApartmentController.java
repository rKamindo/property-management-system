package com.example.tenantmanagementsystem.apartment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/apartments")
public class ApartmentController {
    public ApartmentService apartmentService;
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public List<ApartmentDTO> getAllApartments() {
        return apartmentService.getAllApartments();
    }

    @GetMapping("{id}")
    public ApartmentDTO getApartment(@PathVariable("id") Long id) {
        return apartmentService.getApartment(id);
    }

    @PostMapping
    public void createApartment(@RequestBody Apartment apartment) {
        apartmentService.addApartment(apartment);
    }

    @PutMapping("{id}")
    public void updateApartment(@PathVariable("id") Long id, @RequestBody Apartment updateRequest) {
        apartmentService.updateApartment(id, updateRequest);
    }

    @DeleteMapping("{id}")
    public void deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
    }




}
