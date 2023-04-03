package com.randy.propertymanagementsystem.property;

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
@RequestMapping("api/v1/properties")
public class PropertyController {
    private final PropertyService propertyService;
    private final PropertyDTOMapper propertyDTOMapper;

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
