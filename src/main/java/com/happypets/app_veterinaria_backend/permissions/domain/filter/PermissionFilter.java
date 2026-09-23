package com.happypets.app_veterinaria_backend.permissions.domain.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal permission filter domain representation
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionFilter {
    private String module;
    private String action;
}
