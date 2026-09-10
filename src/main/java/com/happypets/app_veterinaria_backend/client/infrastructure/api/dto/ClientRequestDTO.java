package com.happypets.app_veterinaria_backend.client.infrastructure.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {
    @NotBlank(message = "El DPI es obligatorio")
    private String dpi;
    @NotBlank(message = "El nombre es obligatorio")
    private String  firstName;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
