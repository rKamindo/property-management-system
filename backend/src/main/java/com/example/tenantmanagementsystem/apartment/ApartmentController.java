package com.example.tenantmanagementsystem.apartment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/apartments")
public class ApartmentController {
    public ApartmentService apartmentService;
    public ApartmentDTOMapper apartmentDTOMapper;
    public ApartmentController(ApartmentService apartmentService, ApartmentDTOMapper apartmentDTOMapper) {
        this.apartmentService = apartmentService;
        this.apartmentDTOMapper = apartmentDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<ApartmentDTO>> getAllApartments() {
        List<ApartmentDTO> apartmentDTOS = apartmentService.getAllApartments()
                .stream().map(apartment -> apartmentDTOMapper.apply(apartment))
                .collect(Collectors.toList());
        return new ResponseEntity<>(apartmentDTOS, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable("id") Long id) {
        ApartmentDTO apartmentDTO = apartmentDTOMapper
                .apply(apartmentService.getApartment(id));
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentCreateRequest request) {
        ApartmentDTO apartmentDTO = apartmentDTOMapper
                .apply(apartmentService.addApartment(request));
        return new ResponseEntity<>(apartmentDTO, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public void updateApartment(@PathVariable("id") Long id, @RequestBody Apartment updateRequest) {
        // TODO
//        ApartmentDTO apartmentDTO = apartmentDTOMapper
//                .apply(apartmentService.updateApartment(id, updateRequest));
//        return new ResponseEntity<>()
    }

    @DeleteMapping("{id}")
    public void deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
    }

    @PutMapping("{apartmentId}/tenant/{tenantId}")
    public ResponseEntity<ApartmentDTO> putTenantInApartment(@PathVariable Long apartmentId, @PathVariable Long tenantId) {
        // logic to assign the specified tenant to the specified apartment
        Apartment apartment = apartmentService.setTenantForApartment(apartmentId, tenantId);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.OK);
    }



}
