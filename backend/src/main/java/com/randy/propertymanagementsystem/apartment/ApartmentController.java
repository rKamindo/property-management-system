package com.randy.propertymanagementsystem.apartment;

import com.randy.propertymanagementsystem.property.IPropertyService;
import com.randy.propertymanagementsystem.property.Property;
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
    private final IApartmentService apartmentService;
    private final IApartmentTenantService apartmentTenantService;
    private final IPropertyService propertyService;
    private final ApartmentDTOMapper apartmentDTOMapper;
    private final ApartmentRepository apartmentRepository;

    // TODO NEST ENDPOINTS so APARTMENTS are in the context of a PROPERTY
    // POSSIBLY ADD A QUERY FILTER ? TO FILTER ALL APARTMENTS BY PROPERTY IN THE getApartments()

    @GetMapping()
    public ResponseEntity<List<ApartmentDTO>> getApartmentsForUser() {
        List<ApartmentDTO> apartmentDTOS = apartmentService
                .getApartmentsForUser()
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
            @RequestBody CreateApartmentRequest request) {
        Property property = propertyService.getProperty(request.propertyId());
        Apartment apartment = apartmentService.createApartmentForUser(request, property);
        ApartmentDTO apartmentDTO = apartmentDTOMapper.apply(apartment);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.CREATED);
    }

    @PutMapping("{apartmentId}")
    public ResponseEntity<ApartmentDTO> updateApartment(
            @PathVariable("apartmentId") Long apartmentId,
            @RequestBody UpdateApartmentRequest updateRequest) {
        Apartment apartment = apartmentService.updateApartment(apartmentId, updateRequest);
        ApartmentDTO apartmentDTO = apartmentDTOMapper.apply(apartment);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }

    @DeleteMapping("{apartmentId}")
    public ResponseEntity<?> deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteApartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{apartmentId}/tenant/{tenantId}")
    public ResponseEntity<?> addTenantToApartment(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("apartmentId") Long apartmentId
    ) {
        Apartment apartment = apartmentTenantService
                .addTenantToApartment(tenantId, apartmentId);
        ApartmentDTO apartmentDTO = apartmentDTOMapper.apply(apartment);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }

    @DeleteMapping("{apartmentId}/tenant")
    public ResponseEntity<?> removeTenantFromApartment(
            @PathVariable("apartmentId") Long apartmentId
    ) {
        Apartment apartment = apartmentTenantService
                .removeTenantFromApartment(apartmentId);
        ApartmentDTO apartmentDTO = apartmentDTOMapper.apply(apartment);
        return new ResponseEntity<>(apartmentDTO, HttpStatus.OK);
    }
}
