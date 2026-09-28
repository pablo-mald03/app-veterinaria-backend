package com.happypets.app_veterinaria_backend.logs.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Principal domain representation for logs
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    private Long id;
    private String module;
    private String action;
    private String detail;
    private Long userId;
    private String userRegistry;
    private String userIdentification;
    private Instant createdAt;
}