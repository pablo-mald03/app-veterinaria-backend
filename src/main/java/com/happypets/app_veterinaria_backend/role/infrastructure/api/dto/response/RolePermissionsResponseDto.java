package com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response.PermissionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Role response deto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermissionsResponseDto {
    private Long id;
    private String alias;
    private String name;
    private String description;
    private List<PermissionResponseDto> permissions;
    private Instant createdAt;
    private Instant updatedAt;
}