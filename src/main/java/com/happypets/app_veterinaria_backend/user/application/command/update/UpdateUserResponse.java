package com.happypets.app_veterinaria_backend.user.application.command.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Principal update user response class
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    private Long id;
    private String name;
    private String email;
}