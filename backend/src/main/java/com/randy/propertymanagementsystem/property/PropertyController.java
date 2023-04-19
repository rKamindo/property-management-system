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
    private final IPropertyService propertyService;
    private final PropertyDTOMapper propertyDTOMapper;
    private final TenantDTOMapper tenantDTOMapper;
    private final ApartmentDTOMapper apartmentDTOMapper;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getPropertiesForUser() {
        List<Property> properties = propertyService.getPropertiesForUser();
        List<PropertyDTO> propertyDTOs = properties
                .stream()
                .map(propertyDTOMapper::apply)
                .collect(Collectors.toList());
        return ResponseEntity.ok(propertyDTOs);
    }

    @GetMapping("{propertyId}")
    public ResponseEntity<PropertyDTO> getProperty(
            @PathVariable("propertyId") Long propertyId) {
        Property property = propertyService.getProperty(propertyId);
        PropertyDTO propertyDTO = propertyDTOMapper.apply(property);
        return ResponseEntity.ok(propertyDTO);
    }
    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(
            @RequestBody PropertyCreateRequest request) {
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.createPropertyForUser(request)
        );
        return new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
    }

    @PutMapping("{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable("propertyId") Long propertyId,
            @RequestBody UpdatePropertyRequest updateRequest) {
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.updateProperty(propertyId, updateRequest)
        );
        return ResponseEntity.ok(propertyDTO);
    }

    @DeleteMapping("{propertyId}")
    public ResponseEntity<?> deleteProperty(
            @PathVariable("propertyId") Long propertyId) {
        propertyService.deleteProperty(propertyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{propertyId}/apartments")
    public ResponseEntity<List<ApartmentDTO>> getApartmentsForProperty(
            @PathVariable("propertyId") Long propertyId
    ) {
        List<ApartmentDTO> apartmentDTOs = propertyService.getAllApartments(propertyId)
                .stream()
                .map(apartment -> apartmentDTOMapper.apply(apartment))
                .collect(Collectors.toList());
        return ResponseEntity.ok(apartmentDTOs);
    }

    @GetMapping("{propertyId}/tenants")
    public ResponseEntity<List<TenantDTO>> getTenantsForProperty(
            @PathVariable Long propertyId) {
        List<TenantDTO> tenantDTOs = propertyService.getAllTenants(propertyId)
                .stream()
                .map(tenant -> tenantDTOMapper.apply(tenant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantDTOs);
    }
}
