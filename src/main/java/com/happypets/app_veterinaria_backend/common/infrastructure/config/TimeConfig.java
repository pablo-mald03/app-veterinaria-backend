package com.happypets.app_veterinaria_backend.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

/**
 * Time zone config class
 *
 */
@Configuration
public class TimeConfig {

    public static final ZoneId GUATEMALA_ZONE = ZoneId.of("America/Guatemala");

    @Bean
    public Clock clock() {
        return Clock.system(GUATEMALA_ZONE);
    }
}