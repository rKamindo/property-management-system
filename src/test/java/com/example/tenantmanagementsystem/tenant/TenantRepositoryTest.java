package com.example.tenantmanagementsystem.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TenantRepositoryTest {

    @Autowired
    private TenantRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfTenantExistsByEmail() {
        // given
        String email = "jane@gmail.com";
        Tenant tenant = new Tenant(
                "Jane",
                email,
                "1112223333",
                Gender.FEMALE,
                null
        );
        underTest.save(tenant);

        // when
        boolean expected = underTest.existsTenantByEmail(email);

        // then
        assertTrue(expected);
    }

    @Test
    void itShouldCheckIfStudentsEmailDoesNotExist() {
        // given
        String email = "jane@gmail.com";

        // when
        boolean expected = underTest.existsTenantByEmail(email);

        // then
        assertFalse(expected);
    }
}