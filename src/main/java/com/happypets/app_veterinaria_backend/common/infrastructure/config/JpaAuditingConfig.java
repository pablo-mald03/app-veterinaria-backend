package com.happypets.app_veterinaria_backend.common.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration for jpa auditing tables
 *
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
