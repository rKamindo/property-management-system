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

    // TODO NEST ENDPOINTS so APARTMENTS are in the context of a PROPERTY
    // POSSIBLY ADD A QUERY FILTER ? TO FILTER ALL APARTMENTS BY PROPERTY IN THE getApartments()

    @GetMapping("?propertyId=")
    public ResponseEntity<List<ApartmentDTO>> getApartments(
            @RequestParam Long propertyId
    ) {
        // TODO GET ALL APARTMENTS THAT HAVE THIS PROPERTY ID
        List<ApartmentDTO> apartmentDTOS = apartmentService.getAllApartments()
                .stream()
                .map(apartmentDTOMapper::apply)
                .collect(Collectors.toList());
        return new ResponseEntity<>(apartmentDTOS, HttpStatus.OK);
    }

    @GetMapping("{apartmentId}")
    public ResponseEntity<ApartmentDTO> getApartment(
            @PathVariable("apartmentId") Long apartmentId) {
        Apartment apartment = apartmentService.getApartment(apartmentId);
        ApartmentDTO apartmentDTO = apartmentDTOMapper.apply(apartment);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }

    @PostMapping("?propertyId=")
    public ResponseEntity<ApartmentDTO> createApartment(
            @RequestParam Long propertyId,
            @RequestBody ApartmentCreateRequest request) {
        Apartment apartment = apartmentService.addApartment(propertyId, request);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApartmentDTO> updateApartment(@PathVariable("id") Long id, @RequestBody ApartmentUpdateRequest updateRequest) {
        Apartment apartment = apartmentService.updateApartment(id, updateRequest);
        return new ResponseEntity<>(apartmentDTOMapper.apply(apartment), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
