package com.randy.propertymanagementsystem.tenant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;
    private final TenantDTOMapper tenantDTOMapper;

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getTenants() {
        List<TenantDTO> tenantDTOS = tenantService.getAllTenants()
                .stream()
                .map(tenant -> tenantDTOMapper.apply(tenant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<TenantDTO> getTenant(@PathVariable("id") Long tenantId) {
        TenantDTO tenantDTO = tenantDTOMapper
                .apply(tenantService.getTenant(tenantId));
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TenantDTO> createTenant(@RequestBody TenantCreateRequest request) {
        TenantDTO tenantDTO = tenantDTOMapper.apply(
                tenantService.createTenant(request)
        );
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTenant(@PathVariable("id") Long tenantId) {
        tenantService.deleteTenantById(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<TenantDTO> updateTenant(
            @PathVariable("id") Long tenantId,
            @RequestBody TenantUpdateRequest updateRequest) {
        Tenant tenant = tenantService.updateTenant(tenantId, updateRequest);
        return new ResponseEntity<>(tenantDTOMapper.apply(tenant), HttpStatus.OK);
    }
}
