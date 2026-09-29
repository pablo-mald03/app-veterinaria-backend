package com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Get permission catalog response dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPermissionCatalogResponseDto {
    private List<String> modules;
    private List<String> actions;
}