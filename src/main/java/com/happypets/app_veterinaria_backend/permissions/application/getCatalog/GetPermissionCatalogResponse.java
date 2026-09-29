package com.happypets.app_veterinaria_backend.permissions.application.getCatalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Get permission catalog response class
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPermissionCatalogResponse {
    private List<String> modules;
    private List<String> actions;
}
