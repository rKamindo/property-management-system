package com.example.tenantmanagementsystem.repository;


import com.example.tenantmanagementsystem.model.Tenant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;




import java.util.UUID;


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
        String email = "jjones@gmail.com";
        Tenant tenant = new Tenant(
                new UUID(0,1),
                "Jamila",
                "Jones",
                email,
                "1234567890",
                "123 Home Dr",
                null);
        underTest.save(tenant);

        // when
        boolean expected = underTest.existsByEmail(email);

        // then
        Assertions.assertTrue(expected);
    }


    @Test
    void itShouldCheckIfTenantEmailDoesNotExist() {
        // given
        String email = "jjones@gmail.com";
        Tenant tenant = new Tenant(
                new UUID(0,1),
                "Jamila",
                "Jones",
                email,
                "1234567890",
                "123 Home Dr",
                null);

        // when
        boolean expected = underTest.existsByEmail(email);

        // then
        Assertions.assertFalse(expected);
    }




}
