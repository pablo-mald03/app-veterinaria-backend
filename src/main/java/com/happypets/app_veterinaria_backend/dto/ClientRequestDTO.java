package com.happypets.app_veterinaria_backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequestDTO(
        @NotBlank(message = "El dpi es obligatorio") String dpi,
        @NotBlank(message = "El nombre es obligatorio") String firstName,
        @NotBlank(message = "El apellido es obligatorio") String lastName,
        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 8, max = 15) String phone,
        String address,
        String email
) {}
