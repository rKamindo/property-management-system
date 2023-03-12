package com.example.tenantmanagementsystem.tenant;

import com.example.tenantmanagementsystem.exception.DuplicateResourceException;
import com.example.tenantmanagementsystem.exception.RequestValidationException;
import com.example.tenantmanagementsystem.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Tenant tenant = new Tenant(
                "John",
                "john@gmail.com",
                "1112223333",
                Gender.MALE,
                null);
        List<Tenant> tenants = List.of(tenant);
        when(tenantRepository.findAll()).thenReturn(tenants);

        // when
        List<Tenant> result = underTest.getAllTenants();

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(tenant);
        assertThat(result).isEqualTo(tenants);
        verify(tenantRepository).findAll();
    }

    @Test
    void getTenant() {
        // given
        long id = 10;
        Tenant tenant = new Tenant(
                10L,
                "John",
                "john@gmail.com",
                "777777777",
                Gender.MALE,
                null);
        tenantRepository.save(tenant);

        when(tenantRepository.findById(id)).thenReturn(Optional.of(tenant));

        // when
        Tenant actual = underTest.getTenant(id);

        // then
        assertThat(actual).isEqualTo(tenant);
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
    }

    @Test
    void addTenant() {
        // given
        TenantCreateRequest request = new TenantCreateRequest(
                "Jane",
                "jane@gmail.com",
                "1112223333",
                Gender.FEMALE
        );

        // when
        underTest.addTenant(request);

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
        assertThatThrownBy(() -> underTest.addTenant(tenant))
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
