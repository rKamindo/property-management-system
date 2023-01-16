package com.example.tenantmanagementsystem.controller;

import com.example.tenantmanagementsystem.dto.ApartmentDTO;
import com.example.tenantmanagementsystem.model.Apartment;
import com.example.tenantmanagementsystem.service.ApartmentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/apartment")
public class ApartmentController {
    public ApartmentService apartmentService;
    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Apartment>> getAllApartments() {
        List<Apartment> apartments = apartmentService.findAllApartments();
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ApartmentDTO> getApartmentById(@PathVariable("id") Long id) {
        ApartmentDTO apartmentDTO = apartmentService.findApartmentById(id);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }

    @PostMapping ("/add")
    public ResponseEntity<Apartment> addApartment(@RequestBody Apartment apartment) {
        Apartment newApartment = apartmentService.addApartment(apartment);
        return new ResponseEntity<>(newApartment, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Apartment> updateApartment(@RequestBody Apartment apartment) {
        Apartment updateApartment = apartmentService.updateApartment(apartment);
        return new ResponseEntity<>(updateApartment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
