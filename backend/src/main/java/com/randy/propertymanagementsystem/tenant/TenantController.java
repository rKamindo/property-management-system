package com.randy.propertymanagementsystem.tenant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<List<TenantDTO>> getTenantsForUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<TenantDTO> tenantDTOS = tenantService.getTenantsForUser(userDetails.getUsername())
                .stream()
                .map(tenant -> tenantDTOMapper.apply(tenant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<TenantDTO> getTenant(
            @PathVariable("id") Long tenantId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Tenant tenant = tenantService.getTenant(tenantId);
        if (!tenantService.doesTenantBelongToUser(tenant, userDetails.getUsername()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        TenantDTO tenantDTO = tenantDTOMapper.apply(tenant);
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TenantDTO> createTenant(
            @RequestBody CreateTenantRequest request,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        TenantDTO tenantDTO = tenantDTOMapper.apply(
                tenantService.createTenantForUser(request, userDetails.getUsername())
        );
        return new ResponseEntity<>(tenantDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTenant(
            @PathVariable("id") Long tenantId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Tenant tenant = tenantService.getTenant(tenantId);
        if (!tenantService.doesTenantBelongToUser(tenant, userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        tenantService.deleteTenantById(tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<TenantDTO> updateTenant(
            @PathVariable("id") Long tenantId,
            @RequestBody TenantUpdateRequest updateRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Tenant tenant = tenantService.getTenant(tenantId);
        if (!tenantService.doesTenantBelongToUser(tenant, userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        tenant = tenantService.updateTenant(tenantId, updateRequest);
        return new ResponseEntity<>(tenantDTOMapper.apply(tenant), HttpStatus.OK);
    }
}
