package com.happypets.app_veterinaria_backend.role.infrastructure.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Permission class dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponseDto {
    private Long id;
    private String module;
    private String action;
    private String description;
}