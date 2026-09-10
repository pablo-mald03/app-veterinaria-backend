package com.happypets.app_veterinaria_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.flyway.autoconfigure.FlywayAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;


@SpringBootApplication
public class AppVeterinariaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppVeterinariaBackendApplication.class, args);

	}
}
