package com.randy.propertymanagementsystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.randy.propertymanagementsystem")
public class PropertyManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementSystemApplication.class, args);
	}

}
