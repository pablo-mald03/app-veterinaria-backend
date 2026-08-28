package com.happypets.app_veterinaria_backend.common.infrastructure.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Veterinary API",
                version = "1.0.0",
                contact = @Contact(
                        name = "HappyPets",
                        email = "happypets@gmail.com",
                        url = "https://happypets.org"
                ),
                description = "API for the principal services for the veterinary business",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        /*SERVERS TO TEST THE API*/
        servers = @Server(
                url = "http://localhost:8080/api/v1",
                description = "Local development"
        )
)

/*DECORATOR FOR THE PROTECTED ENDPOINTS*/
@SecurityScheme(
        name = "Bearer Authentication",
        description = "Authentication with JWT",
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP
)
@Configuration
//Principal config for open api documentation
public class OpenAPIConfig {
}
