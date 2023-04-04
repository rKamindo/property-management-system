package com.randy.propertymanagementsystem.apartment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping()
    public ResponseEntity<List<ApartmentDTO>> getApartmentsForUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<ApartmentDTO> apartmentDTOS = apartmentService
                .getApartmentsForUser(userDetails.getUsername())
                .stream()
                .map(apartmentDTOMapper)
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

    @PostMapping()
    public ResponseEntity<ApartmentDTO> createApartment(
            @RequestBody CreateApartmentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        Apartment apartment = apartmentService.createApartmentForUser(
                request,
                userDetails.getUsername());
        ApartmentDTO apartmentDTO = apartmentDTOMapper.apply(apartment);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.CREATED);
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

    @PutMapping("{apartmentId}/tenant/{tenantid}")
    public ResponseEntity<?> addTenantToApartment(
            @PathVariable("apartmentId") Long apartmentId,
            @PathVariable("tenantId") Long tenantId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // todo verify tenant and apartment belong to user?
        apartmentService.addTenantToApartment(tenantId, apartmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{apartmentId}/tenant")
    public ResponseEntity<?> deleteTenantFromApartment(
            @PathVariable("apartmentId") Long apartmentId,
            @PathVariable("tenantId") Long tenantId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // todo verify tenant and apartment belong to user?
        apartmentService.removeTenantFromApartment(apartmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
