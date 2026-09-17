package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Principal dto to register the user
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDto {

    @NotBlank(message = "La identificación del usuario no puede venir vacía")
    @Size(min = 8, max = 13, message = "La identificación debe tener entre 8 y 13 caracteres")
    private String identification;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 60, message = "El nombre debe tener como máximo 60 caracteres")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 60, message = "El apellido debe tener como máximo 60 caracteres")
    private String firstName;

    @NotBlank(message = "El username no puede estar vacío")
    @Size(max = 50, message = "El username debe tener como máximo 50 caracteres")
    private String userRegistry;

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    @Pattern(
            regexp = "^\\+?[0-9\\s\\-\\(\\)]{8,20}$",
            message = "El formato del teléfono no es válido (ej. +502 1234 5678, +502-1234-5678 o 12345678)"
    )
    private String phone;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String rawPassword;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 150, message = "El correo no debe superar los 150 caracteres")
    private String email;

    @NotNull(message = "El conjunto de roles no puede ser nulo")
    @NotEmpty(message = "Debe asignar al menos un rol al usuario")
    private Set<@NotBlank(message = "El alias del rol no puede estar vacío") String> roleAliases;
}