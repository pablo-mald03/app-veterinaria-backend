package com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Permission pagination response class dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPermissionsResponseDto {
    private List<PermissionResponseDto> permissions;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}