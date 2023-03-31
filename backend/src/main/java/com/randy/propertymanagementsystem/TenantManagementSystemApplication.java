package com.randy.tenantmanagementsystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.tenantmanagementsystem")
public class TenantManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantManagementSystemApplication.class, args);
	}

}
