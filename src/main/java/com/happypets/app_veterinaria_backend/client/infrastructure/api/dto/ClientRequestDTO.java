package com.happypets.app_veterinaria_backend.client.infrastructure.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {
    @NotBlank(message = "El DPI es obligatorio")
    @Size(max = 13, min = 13, message = "El DPI debe ser de 13 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El DPI solo debe contener números")
    private String dpi;
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    private String email;
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    @Pattern(regexp = "^[0-9]{8}$", message = "El teléfono debe tener exactamente 8 dígitos")
    private String phone;
    @Size(max = 180, message = "La dirección no puede superar los 180 caracteres")
    private String address;
}
