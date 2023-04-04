package com.randy.propertymanagementsystem.property;

import com.randy.propertymanagementsystem.apartment.ApartmentDTO;
import com.randy.propertymanagementsystem.apartment.ApartmentDTOMapper;
import com.randy.propertymanagementsystem.apartment.ApartmentService;
import com.randy.propertymanagementsystem.tenant.TenantDTO;
import com.randy.propertymanagementsystem.tenant.TenantDTOMapper;
import com.randy.propertymanagementsystem.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/properties")
public class PropertyController {
    private final PropertyService propertyService;
    private final PropertyDTOMapper propertyDTOMapper;
    private final TenantService tenantService;
    private final TenantDTOMapper tenantDTOMapper;
    private final ApartmentService apartmentService;
    private final ApartmentDTOMapper apartmentDTOMapper;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getPropertiesForUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String userEmail = userDetails.getUsername();
        List<Property> properties = propertyService.getAllPropertiesForUser(userEmail);
        List<PropertyDTO> propertyDTOs = properties
                .stream()
                .map(propertyDTOMapper::apply)
                .collect(Collectors.toList());
        return ResponseEntity.ok(propertyDTOs);
    }

    @GetMapping("{id}")
    public ResponseEntity<PropertyDTO> getProperty(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Property property = propertyService.getProperty(id);
        if (!propertyService.doesPropertyBelongToUser(property, userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        PropertyDTO propertyDTO = propertyDTOMapper.apply(property);
        return ResponseEntity.ok(propertyDTO);
    }

    @GetMapping("{propertyId}/tenants")
    public ResponseEntity<List<TenantDTO>> getTenantsForProperty(
            @PathVariable Long propertyId) {
        List<TenantDTO> tenantDTOs = tenantService.getTenantsForProperty(propertyId)
                .stream()
                .map(tenant -> tenantDTOMapper.apply(tenant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantDTOs);
    }

    @GetMapping("{propertyId}/apartments")
    public ResponseEntity<List<ApartmentDTO>> getApartmentsForProperty(
            @PathVariable("propertyId") Long propertyId
    ) {
        List<ApartmentDTO> apartmentDTOs = apartmentService.getApartmentsForProperty(propertyId)
                .stream()
                .map(apartment -> apartmentDTOMapper.apply(apartment))
                .collect(Collectors.toList());
        return ResponseEntity.ok(apartmentDTOs);
    }

    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(
            @RequestBody PropertyCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.createPropertyForUser(request, userDetails.getUsername())
        );
        return new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable("id") Long id,
            @RequestBody UpdatePropertyRequest updateRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Property property = propertyService.getProperty(id);
        if (!propertyService.doesPropertyBelongToUser(property, userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.updateProperty(id, updateRequest)
        );
        return ResponseEntity.ok(propertyDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProperty(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        propertyService.deleteProperty(id, userDetails.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
