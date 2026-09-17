package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal role detail dto response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDetailResponseDto {
    private Long id;
    private String alias;
    private String name;
    private String description;
}
