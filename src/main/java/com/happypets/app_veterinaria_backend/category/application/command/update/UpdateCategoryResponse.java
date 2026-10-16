package com.happypets.app_veterinaria_backend.category.application.command.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update category class request
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryResponse {

    private String name;
}