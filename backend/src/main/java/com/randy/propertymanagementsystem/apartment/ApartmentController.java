package com.randy.propertymanagementsystem.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/apartments")
public class ApartmentController {
    private final ApartmentService apartmentService;
    private final ApartmentDTOMapper apartmentDTOMapper;

    @GetMapping
    public ResponseEntity<List<ApartmentDTO>> getApartments() {
        List<ApartmentDTO> apartmentDTOS = apartmentService.getAllApartments()
                .stream()
                .map(apartmentDTOMapper::apply)
                .collect(Collectors.toList());
        return new ResponseEntity<>(apartmentDTOS, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable("id") Long id) {
        Apartment apartment = apartmentService.getApartment(id);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentCreateRequest request) {
        Apartment apartment = apartmentService.addApartment(request);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(@PathVariable("id") Long id, @RequestBody ApartmentUpdateRequest updateRequest) {
        Apartment apartment = apartmentService.updateApartment(id, updateRequest);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
    }

    @PutMapping("{apartmentId}/tenant/{tenantId}")
    public ResponseEntity<ApartmentDTO> putTenantInApartment(@PathVariable Long apartmentId, @PathVariable Long tenantId) {
        // logic to assign the specified tenant to the specified apartment
        Apartment apartment = apartmentService.putTenantInApartment(apartmentId, tenantId);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.OK);
    }

    @DeleteMapping("{apartmentId}/tenant")
    public ResponseEntity<ApartmentDTO> removeTenantFromApartment(@PathVariable Long apartmentId) {
        Apartment apartment = apartmentService.removeTenantFromApartment(apartmentId);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.OK);
    }
}
