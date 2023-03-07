package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.exception.DuplicateResourceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private TenantDTOMapper tenantDTOMapper;
    private TenantService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TenantService(tenantRepository, tenantDTOMapper);
    }

    @Test
    void canAddTenant() {
        // given
        Tenant tenant = new Tenant(
                "Jane",
                "jane@gmail.com",
                "1112223333",
                Gender.FEMALE,
                null
        );

        // when
        underTest.addTenant(tenant);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository)
                .save(tenantArgumentCaptor.capture());

        Tenant capturedTenant = tenantArgumentCaptor.getValue();

        assertEquals(tenant, capturedTenant);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Tenant tenant = new Tenant(
                "Jane",
                "jane@gmail.com",
                "1112223333",
                Gender.FEMALE,
                null
        );

        // when
        when(tenantRepository.existsTenantByEmail(tenant.getEmail()))
                .thenReturn(true);

        // then
        assertThrows(
                DuplicateResourceException.class,
                () -> underTest.addTenant(tenant));
    }
}
