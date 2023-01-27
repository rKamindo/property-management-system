package com.example.tenantmanagementsystem.service;

import com.example.tenantmanagementsystem.exception.ApartmentNotFoundException;
import com.example.tenantmanagementsystem.exception.BadRequestException;
import com.example.tenantmanagementsystem.model.Apartment;
import com.example.tenantmanagementsystem.model.Tenant;
import com.example.tenantmanagementsystem.repository.ApartmentRepository;
import com.example.tenantmanagementsystem.repository.TenantRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private ApartmentRepository apartmentRepository;
    @Mock
    private ModelMapper modelMapper;

    private TenantService underTest;
    private Tenant tenant;
    private Apartment apartment;

    @BeforeEach
    void setUp() {
        underTest = new TenantService(tenantRepository, apartmentRepository, modelMapper);
        tenant = new Tenant(
                new UUID(0,1),
                "Jamila",
                "Jones",
                "jjones@gmail.com",
                "1234567890",
                "123 Home Dr",
                null);

        apartment = new Apartment(
                "101",
                1,
                "101 Address Ln",
                500.0,
                null);
    }

    @AfterEach
    void tearDown() {
        tenantRepository.deleteAll();
        apartmentRepository.deleteAll();
    }
    @Test
    void canFindAllTenants() {
        //when
        underTest.getAllTenants();
        // then
        verify(tenantRepository).findAll();
    }

    @Test
    void canAddTenant() {
        // when
        underTest.addTenant(tenant);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository)
                .save(tenantArgumentCaptor.capture());
    }
}