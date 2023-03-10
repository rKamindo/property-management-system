package com.example.tenantmanagementsystem.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TenantRepositoryTest {

    @Autowired
    private TenantRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void existsTenantByEmail() {
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
        assertThat(expected).isTrue();
    }

    @Test
    void existsTenantByEmailFailWhenEmailNotPresent() {
        // given
        String email = "jane@gmail.com";

        // when
        boolean expected = underTest.existsTenantByEmail(email);

        // then
        assertThat(expected).isFalse();
    }
}