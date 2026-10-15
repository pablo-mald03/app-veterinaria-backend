package com.happypets.app_veterinaria_backend.category.application.query.getById;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Get by id categories response
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryByIdResponse {

    private Long id;
    private String name;
    private String description;
    private boolean status;
}