package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal update user request dto class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {

    @Size(min = 8, max = 13, message = "La identificación debe tener entre 8 y 13 caracteres")
    private String identification;

    @Size(max = 60, message = "El nombre debe tener como máximo 60 caracteres")
    private String name;

    @Size(max = 60, message = "El apellido debe tener como máximo 60 caracteres")
    private String firstName;

    @Size(max = 50, message = "El username debe tener como máximo 50 caracteres")
    private String userRegistry;

    @Size(min = 8, max = 20, message = "El telefono debe tener entre 8 y 20 caracteres")
    @Pattern(
            regexp = "^\\+?[0-9\\s\\-\\(\\)]{8,20}$",
            message = "El formato del telefono no es valido (ej. +502 1234 5678 o 12345678)"
    )
    private String phone;

    @Email(message = "El formato del correo electronico no es válido")
    @Size(max = 150, message = "El correo no debe superar los 150 caracteres")
    private String email;
}