package com.example.tenantmanagementsystem.controller;

import com.example.tenantmanagementsystem.dto.TenantDTO;
import com.example.tenantmanagementsystem.repository.TenantRepository;
import com.example.tenantmanagementsystem.service.TenantService;
import com.example.tenantmanagementsystem.model.Tenant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tenants")
public class TenantController {
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    public TenantController(TenantService tenantService,
                            TenantRepository tenantRepository) {
        this.tenantService = tenantService;
        this.tenantRepository = tenantRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        List<TenantDTO> tenantDTOS = tenantService.getAllTenants();
        return new ResponseEntity<>(tenantDTOS, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TenantDTO> getTenantById(@PathVariable("id") Long id) {
        TenantDTO tenantDTO = tenantService.getTenantById(id);
        return new ResponseEntity<>(tenantDTO, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<TenantDTO> createTenant(@RequestBody Tenant tenant) {
        TenantDTO newTenantDTO = tenantService.addTenant(tenant);
        return new ResponseEntity<>(newTenantDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TenantDTO> updateTenant(@PathVariable("id") Long id, @RequestBody Tenant tenantDetails) {
        TenantDTO updateTenantDTO = tenantService.updateTenant(id, tenantDetails);
        return new ResponseEntity<>(updateTenantDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTenant(@PathVariable("id") Long id) {
        tenantService.deleteTenant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
