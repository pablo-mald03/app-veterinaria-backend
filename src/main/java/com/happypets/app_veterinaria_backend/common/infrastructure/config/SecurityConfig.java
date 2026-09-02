package com.happypets.app_veterinaria_backend.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //Principal method to allows the request forgery
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        /*Request matcher for the pagest without auth */
                        .requestMatchers(
                                       /* "/users/login",
                                        "/users/register",*/
                                /*API DOCUMENTATION*/
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"

                        ).permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        //TODO
                        // .requestMatchers(
                        //         "/auth/login",
                        //         "/auth/register",
                        //         "/auth/refresh"
                        // ).permitAll()
                        .anyRequest().authenticated()

                )
                /*
                 * filter with jwt
                 * */
                //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
