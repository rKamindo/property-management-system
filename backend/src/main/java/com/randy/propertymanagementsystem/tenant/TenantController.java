package com.randy.propertymanagementsystem.tenant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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
    public ResponseEntity<List<TenantDTO>> getAllTenantsForUser() {
        List<TenantDTO> tenantDTOS = tenantService.getTenantsForUser()
                .stream()
                .map(tenant -> tenantDTOMapper.apply(tenant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<TenantDTO> getTenant(
            @PathVariable("id") Long id) {
        TenantDTO tenantDTO = tenantDTOMapper.apply(
                tenantService.getTenant(id)
        );
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TenantDTO> createTenant(
            @RequestBody CreateTenantRequest request) {
        TenantDTO tenantDTO = tenantDTOMapper.apply(
                tenantService.createTenantForUser(request)
        );
        return new ResponseEntity<>(tenantDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTenant(
            @PathVariable("id") Long id) {
        tenantService.deleteTenant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<TenantDTO> updateTenant(
            @PathVariable("id") Long id,
            @RequestBody TenantUpdateRequest updateRequest) {
        Tenant tenant = tenantService.updateTenant(id, updateRequest);
        return new ResponseEntity<>(tenantDTOMapper.apply(tenant), HttpStatus.OK);
    }
}
