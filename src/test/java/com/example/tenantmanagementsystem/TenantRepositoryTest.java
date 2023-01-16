//package com.example.tenantmanagementsystem;
//
//import com.example.tenantmanagementsystem.model.Apartment;
//import com.example.tenantmanagementsystem.model.Tenant;
//import com.example.tenantmanagementsystem.repository.ApartmentRepository;
//import com.example.tenantmanagementsystem.repository.TenantRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class TenantRepositoryTest {
//
//    @Autowired
//    TenantRepository tenantRepository;
//    @Autowired
//    ApartmentRepository apartmentRepository;
//    Tenant tenant;
//    Apartment apartment;
//    Long tenantId, apartmentId;
//    @BeforeEach
//    public void setUp() {
//        tenant = new Tenant("Martin", "Page", "1234567890", "tenantcode1", null);
//        apartment = new Apartment("101", 500, null);
//        tenant.setApartment(apartment);
//        apartment.setTenant(tenant);
//        Tenant savedTenant = tenantRepository.save(tenant);
//        Apartment savedApartment = apartmentRepository.save(apartment);
//        tenantId = savedTenant.getId();
//        apartmentId = savedApartment.getId();
//    }
//
//    @Test
//    public void testGetTenantById() {
//        System.out.println(tenantId);
//        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
//        Assertions.assertNotNull(tenant);
//    }
//
//    @Test
//    public void testGetTenantWithApartment() {
//        Tenant tenant = tenantRepository.findById(tenantId).orElse(null);
//        Assertions.assertNotNull(tenant.getApartment());
//        Assertions.assertEquals("Martin", apartment.getTenant().getFirstName());
//    }
//
//    @Test
//    public void testGetApartmentWithTenant() {
//        Apartment apartment = apartmentRepository.findById(apartmentId).orElse(null);
//        Assertions.assertNotNull(apartment.getTenant());
//        Assertions.assertEquals("101", tenant.getApartment().getApartmentNumber());
//    }
//
//    @Test
//    public void testDeleteTenantNotCascadingToApartment() {
//        tenantRepository.deleteById(tenantId);
//        Assertions.assertNull(tenantRepository.findById(tenantId).orElse(null));
//        Assertions.assertNotNull(apartmentRepository.findById(apartmentId).orElse(null));
//    }
//
//
//}
