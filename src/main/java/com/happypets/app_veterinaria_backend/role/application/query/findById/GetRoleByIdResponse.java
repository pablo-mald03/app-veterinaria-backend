package com.happypets.app_veterinaria_backend.role.application.query.findById;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal get by id role response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRoleByIdResponse {
    private Long id;
    private String alias;
    private String name;
    private String description;
}
