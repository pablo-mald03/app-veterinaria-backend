package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Summary role dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleSummaryDto {
    private Long id;
    private String name;
}