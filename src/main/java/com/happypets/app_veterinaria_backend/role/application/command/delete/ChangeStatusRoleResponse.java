package com.happypets.app_veterinaria_backend.role.application.command.delete;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * disable role class response
 *
 */
@Data
@AllArgsConstructor
public class ChangeStatusRoleResponse {
    private Long roleId;
    private String name;
    private boolean active;

}
