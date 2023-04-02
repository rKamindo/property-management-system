package com.randy.propertymanagementsystem.tenant;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
    public ResponseEntity<List<TenantDTO>> getTenantsForCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        // use the userEmail to retrieve list of tenants for the current
        List<TenantDTO> tenantDTOS = tenantService.getTenantsForUser(userEmail)
                .stream()
                .map(tenant -> tenantDTOMapper.apply(tenant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tenantDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<TenantDTO> getTenant(@PathVariable("id") Long tenantId) {
        TenantDTO tenantDTO = tenantDTOMapper
                .apply(tenantService.getTenant(tenantId));
        return new ResponseEntity<>(    tenantDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TenantDTO> createTenant(
            @RequestBody TenantCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        String userEmail = userDetails.getUsername();
        TenantDTO tenantDTO = tenantDTOMapper.apply(
                tenantService.createTenantForUser(request, userEmail)
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
            @RequestBody TenantUpdateRequest updateRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        Tenant tenant = tenantService.updateTenant(tenantId, updateRequest);
        return new ResponseEntity<>(tenantDTOMapper.apply(tenant), HttpStatus.OK);
    }
}
