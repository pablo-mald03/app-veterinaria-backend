package com.happypets.app_veterinaria_backend.permissions.infrastructure.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get all logs query dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPermissionQueryDto {
    private String module;
    private String action;

}