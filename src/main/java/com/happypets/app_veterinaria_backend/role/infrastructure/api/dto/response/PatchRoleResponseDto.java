package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal update role request dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchRoleResponseDto {

    private Long roleId;
    private String alias;
}
