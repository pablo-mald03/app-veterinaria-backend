package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal dto to register the user
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDto {

    @NotBlank(message = "La identificación es obligatoria")
    @Size(min = 8, max = 13, message = "La identificacion debe tener entre 8 y 13 caracteres")
    private String identification;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String firstName;

    @NotBlank
    @Email(message = "El correo electronico no es válido")
    private String email;

    @NotBlank
    @Size(max = 15, message = "El numero de telefono debe tener como maximo 15 caracteres")
    private String phone;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El rol del usuario no puede ser nulo")
    private String role;
}
