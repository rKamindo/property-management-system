package com.randy.propertymanagementsystem.property;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<List<PropertyDTO>> getProperties() {

        // TODO retrieve the client associated with the current request

        // TODO retrieve the properties associated with the client
        List<PropertyDTO> propertyDTOs = propertyService.getAllProperties()
                .stream()
                .map(propertyDTOMapper::apply)
                .collect(Collectors.toList());
        return ResponseEntity.ok(propertyDTOs);
    }

    @GetMapping("{id}")
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable("id") Long id) {
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.getProperty(id)
        );
        return ResponseEntity.ok(propertyDTO);
    }

    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyCreateRequest createRequest) {
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.createProperty(createRequest)
        );
        return new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable("id") Long id,
            @RequestBody PropertyUpdateRequest updateRequest) {
        PropertyDTO propertyDTO = propertyDTOMapper.apply(
                propertyService.updateProperty(id, updateRequest)
        );
        return ResponseEntity.ok(propertyDTO);
    }


}
