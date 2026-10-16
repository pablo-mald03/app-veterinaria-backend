package com.happypets.app_veterinaria_backend.category.application.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal clas response when the role was created
 *
 */
@Data
@AllArgsConstructor
public class CreateCategoryResponse {

    private String name;
}
