package com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal recover password request dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordRequestDto {

    @NotBlank(message = "El DPI es obligatorio")
    @Size(min = 8, max = 13, message = "El DPI debe tener entre 8 y 13 caracteres")
    private String dpi;

    @NotBlank(message = "El correo es obligatorio")
    @Size(max = 150, message = "El correo no puede tener mas de 150 caracteres")
    @Email(message = "El correo no tiene un formato valido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "La confirmacion de contraseña es obligatoria")
    private String confirmationPassword;

}