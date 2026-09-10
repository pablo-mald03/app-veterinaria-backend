package com.happypets.app_veterinaria_backend.common.infrastructure.config;

import com.happypets.app_veterinaria_backend.user.infrastructure.database.repository.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * ASSIGNMENT ACTIONS TO THE INTERFACE
 *
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final QueryUserRepository queryUserRepository;

    /**
     * Interface to define the user details to get the information to validate the JWT claims
     *
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> queryUserRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * Interface to define the method to encode the password
     *
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    /**
     * Method to return the authentication provider
     *
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Bean to access to the authenticatio manager configuration
     *
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
