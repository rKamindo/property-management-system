package com.example.tenantmanagementsystem.tenant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/tenants")
public class TenantController {
    private final TenantService tenantService;
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getTenants() {
        List<TenantDTO> tenantDTOS = tenantService.getAllTenants();
        return new ResponseEntity<>(tenantDTOS, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TenantDTO> getTenant(@PathVariable("id") Long tenantId) {
        TenantDTO tenant = tenantService.getTenant(tenantId);
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TenantDTO> createTenant(@RequestBody TenantCreateRequest request) {
        TenantDTO tenantDTO = tenantService.addTenant(request);
        return new ResponseEntity<>(tenantDTO, HttpStatus.CREATED);
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
        // todo - use a TenantUpdateRequest record that only has updatable fields

        TenantDTO tenantDTO = tenantService.updateTenant(tenantId, updateRequest);
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }


}
