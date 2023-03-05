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
    public List<TenantDTO> getTenants() {
        return tenantService.getAllTenants();
    }

    @GetMapping("{id}")
    public TenantDTO getTenant(@PathVariable("id") Long tenantId) {
        return tenantService.getTenant(tenantId);
    }
    @PostMapping
    public void createTenant(@RequestBody Tenant tenant) {
        tenantService.addTenant(tenant);
    }

    @DeleteMapping("{id}")
    public void deleteTenant(@PathVariable("id") Long tenantId) {
        tenantService.deleteTenantById(tenantId);
    }

    @PutMapping("{id}")
    public void updateTenant(
            @PathVariable("id") Long tenantId,
            @RequestBody Tenant updateRequest) {
        // todo - use a TenantUpdateRequest record that only has updatable fields

        tenantService.updateTenant(tenantId, updateRequest);
    }


}
