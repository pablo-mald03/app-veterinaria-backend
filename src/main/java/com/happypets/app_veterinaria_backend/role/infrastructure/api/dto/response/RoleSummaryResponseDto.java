package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

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
public class RoleSummaryResponseDto {
    private Long id;
    private String alias;
    private String name;
    private String description;
    private boolean active;
}