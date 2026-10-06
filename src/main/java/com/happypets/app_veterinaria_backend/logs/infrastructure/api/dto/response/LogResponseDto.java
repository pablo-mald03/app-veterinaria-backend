package com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Log response class dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponseDto {
    private Long id;
    private String module;
    private String action;
    private String detail;
    private Long userId;
    private String userRegistry;
    private String userIdentification;
    private Instant createdAt;
}