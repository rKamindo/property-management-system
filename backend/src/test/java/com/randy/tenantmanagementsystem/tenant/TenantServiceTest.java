package com.randy.tenantmanagementsystem.tenant;

import com.randy.tenantmanagementsystem.exception.DuplicateResourceException;
import com.randy.tenantmanagementsystem.exception.RequestValidationException;
import com.randy.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {
    @Mock
    private TenantRepository tenantRepository;
    private TenantService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TenantService(tenantRepository);
    }

    @Test
    void getAllTenants() {
        List<Tenant> tenants = new ArrayList<>();
        tenants.add(new Tenant(
                "John Doe",
                "johndoe@gmail.com",
                "1234567890",
                Gender.MALE,
                null));
        tenants.add(new Tenant(
                "Jane Smith",
                "janesmith@gmail.com",
                "0987654321",
                Gender.MALE,
                null));
        when(tenantRepository.findAll()).thenReturn(tenants);

        // when
        List<Tenant> result = underTest.getAllTenants();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        assertThat(result.get(1).getName()).isEqualTo("Jane Smith");
        verify(tenantRepository, times(1)).findAll();
    }

    @Test
    void getTenant() {
        // given
        long id = 1;
        Tenant tenant = new Tenant(
                "John Doe",
                "johndoe@gmail.com",
                "1234567890",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        // when
        Tenant result = underTest.getTenant(id);

        // then
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(tenantRepository, times(1)).findById(id);
    }

    @Test
    void willThrowWhenGetTenantReturnEmptyOptional() {
        // given
        long id = 10;

        // when
        when(tenantRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> underTest.getTenant(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("tenant with id [%s] not found".formatted(id));
        verify(tenantRepository, times(1)).findById(id);
    }

    @Test
    void createTenant() {
        // given
        TenantCreateRequest request = new TenantCreateRequest(
                "John Doe", "johndoe@example.com", "1234567890", Gender.MALE);

        // when
        underTest.createTenant(request);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository)
                .save(tenantArgumentCaptor.capture());

        Tenant capturedTenant = tenantArgumentCaptor.getValue();

        assertThat(capturedTenant.getId()).isNull();
        assertThat(capturedTenant.getName()).isEqualTo(request.name());
        assertThat(capturedTenant.getEmail()).isEqualTo(request.email());
        assertThat(capturedTenant.getPhone()).isEqualTo(request.phone());
        assertThat(capturedTenant.getGender()).isEqualTo(request.gender());
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        TenantCreateRequest tenant = new TenantCreateRequest(
                "Jane",
                "jane@gmail.com",
                "1112223333",
                Gender.FEMALE
        );

        // when
        when(tenantRepository.existsTenantByEmail(tenant.email()))
                .thenReturn(true);

        // then
        assertThatThrownBy(() -> underTest.createTenant(tenant))
                .isInstanceOf(DuplicateResourceException.class)
                        .hasMessage("email already taken");

        verify(tenantRepository, never()).save(any());
    }

    @Test
    void deleteTenantById() {
        // given
        long id = 10;

        when(tenantRepository.existsById(id)).thenReturn(true);

        // when
        underTest.deleteTenantById(id);

        // then
        verify(tenantRepository).deleteById(id);
    }

    @Test
    void willThrowDeleteTenantByIdNotExists() {
        // given
        long id = 10;

        when(tenantRepository.existsById(id)).thenReturn(false);

        // when
        assertThatThrownBy(() -> underTest.deleteTenantById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("tenant with id [%s] not found".formatted(id));

        // then
        verify(tenantRepository, never()).deleteById(id);
    }

    @Test
    void canUpdateAllTenantProperties() {
        // given
        long id = 10;
        Tenant tenant = new Tenant(
                id,
                "John",
                "john@gmail.com",
                "5555555555",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        String newEmail = "johnson@yahoo.com";

        TenantUpdateRequest updateRequest = new TenantUpdateRequest(
                "Johnson", newEmail, "6667778888");

        when(tenantRepository.existsTenantByEmail(newEmail)).thenReturn(false);

        // when
        underTest.updateTenant(id, updateRequest);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository).save(tenantArgumentCaptor.capture());
        Tenant capturedTenant = tenantArgumentCaptor.getValue();

        assertThat(capturedTenant.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedTenant.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedTenant.getPhone()).isEqualTo(updateRequest.phone());
    }

    @Test
    void canUpdateOnlyTenantName() {
        // given
        long id = 10;
        Tenant tenant = new Tenant(
                id,
                "John",
                "john@gmail.com",
                "5555555555",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        TenantUpdateRequest updateRequest = new TenantUpdateRequest(
                "Johnson", null, null);

        // when
        underTest.updateTenant(id, updateRequest);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
        ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository).save(tenantArgumentCaptor.capture());
        Tenant capturedTenant = tenantArgumentCaptor.getValue();

        assertThat(capturedTenant.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedTenant.getEmail()).isEqualTo(tenant.getEmail());
        assertThat(capturedTenant.getPhone()).isEqualTo(tenant.getPhone());
    }

    @Test
    void canUpdateOnlyPhone() {
        // given
        long id = 10;
        Tenant tenant = new Tenant(
                id,
                "John",
                "john@gmail.com",
                "5555555555",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        TenantUpdateRequest updateRequest = new TenantUpdateRequest(
                null, null, "6667778888");

        // when
        underTest.updateTenant(id, updateRequest);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository).save(tenantArgumentCaptor.capture());
        Tenant capturedTenant = tenantArgumentCaptor.getValue();

        assertThat(capturedTenant.getName()).isEqualTo(tenant.getName());
        assertThat(capturedTenant.getEmail()).isEqualTo(tenant.getEmail());
        assertThat(capturedTenant.getPhone()).isEqualTo(updateRequest.phone());
    }

    @Test
    void canUpdateOnlyEmail() {
        // given
        long id = 10;
        Tenant tenant = new Tenant(
                id,
                "John",
                "john@gmail.com",
                "5555555555",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        TenantUpdateRequest updateRequest = new TenantUpdateRequest(
                null, "johnson@yahoo.com", null);

        // when
        underTest.updateTenant(id, updateRequest);

        // then
        ArgumentCaptor<Tenant> tenantArgumentCaptor =
                ArgumentCaptor.forClass(Tenant.class);

        verify(tenantRepository).save(tenantArgumentCaptor.capture());
        Tenant capturedTenant = tenantArgumentCaptor.getValue();

        assertThat(capturedTenant.getName()).isEqualTo(tenant.getName());
        assertThat(capturedTenant.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedTenant.getPhone()).isEqualTo(tenant.getPhone());
    }

    @Test
    void willThrowWhenTryingToUpdateTenantEmailWhenEmailTaken() {
        // given
        long id = 10;
        Tenant tenant = new Tenant(
                id,
                "John",
                "john@gmail.com",
                "5555555555",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        String newEmail = "johnson@yahoo.com";

        TenantUpdateRequest updateRequest = new TenantUpdateRequest(
                null, newEmail, null);

        when(tenantRepository.existsTenantByEmail(newEmail)).thenReturn(true);

        // when
        assertThatThrownBy(() -> underTest.updateTenant(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        // then
        verify(tenantRepository, never()).save(any());
    }

    @Test
    void willThrowWhenTenantUpdateHasNoChanges() {
        Long id = 10L;
        Tenant tenant = new Tenant(
                id,
                "John",
                "john@gmail.com",
                "5555555555",
                Gender.MALE,
                null);
        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        TenantUpdateRequest updateRequest = new TenantUpdateRequest(
                tenant.getName(), tenant.getEmail(), tenant.getPhone());

        // when
        assertThatThrownBy(() -> underTest.updateTenant(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // then
        verify(tenantRepository, never()).save(any());
    }
}
