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
    public ResponseEntity<List<ApartmentDTO>> getAllApartments() {
        List<ApartmentDTO> apartments = apartmentService.getAllApartments();
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApartmentDTO> getApartmentById(@PathVariable("id") Long id) {
        ApartmentDTO apartmentDTO = apartmentService.findApartmentById(id);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody Apartment apartment) {
        ApartmentDTO newApartment = apartmentService.addApartment(apartment);
        return new ResponseEntity<>(newApartment, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(@PathVariable("id") Long id, @RequestBody Apartment apartmentDetails) {
        ApartmentDTO updateApartment = apartmentService.updateApartment(id, apartmentDetails);
        return new ResponseEntity<>(updateApartment, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
