package com.happypets.app_veterinaria_backend.role.infrastructure.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Role dto response class
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRoleResponseDto {
    private List<RoleResponseDto> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
}
