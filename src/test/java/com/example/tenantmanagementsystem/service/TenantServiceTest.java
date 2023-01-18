package com.example.tenantmanagementsystem.service;

import com.example.tenantmanagementsystem.model.Tenant;
import com.example.tenantmanagementsystem.repository.ApartmentRepository;
import com.example.tenantmanagementsystem.repository.TenantRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

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

    @BeforeEach
    void setUp() {
        underTest = new TenantService(tenantRepository, apartmentRepository, modelMapper);
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
        Tenant tenant = new Tenant(
                new UUID(0,1),
                "Jamila",
                "Jones",
                "jjones@gmail.com",
                "1234567890",
                "123 Home Dr",
                null);
        underTest.addTenant(tenant);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository)
                .save(tenantArgumentCaptor.capture());
    }
}