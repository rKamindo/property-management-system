package com.example.tenantmanagementsystem.controller;

import com.example.tenantmanagementsystem.service.TenantService;
import com.example.tenantmanagementsystem.model.Tenant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    private TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tenant>> getAllTenants() {
        List<Tenant> tenants = tenantService.findAllTenants();
        return new ResponseEntity<>(tenants, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable("id") Long id) {
        Tenant tenant = tenantService.findTenantFindById(id);
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tenant> addTenant(@RequestBody Tenant tenant) {
        Tenant newTenant = tenantService.addTenant(tenant);
        return new ResponseEntity<>(newTenant, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Tenant> updateTenant(@RequestBody Tenant tenant) {
        Tenant updateTenant = tenantService.updateTenant(tenant);
        return new ResponseEntity<>(updateTenant, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTenant(@PathVariable("id") Long id) {
        tenantService.deleteTenant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
