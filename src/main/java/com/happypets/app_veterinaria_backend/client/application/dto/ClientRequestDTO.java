package com.happypets.app_veterinaria_backend.client.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(
        @NotBlank(message = "El DPI es obligatorio") String dpi,
        @NotBlank(message = "El nombre es obligatorio") String  firstName,
        @NotBlank(message = "El apellido es obligatorio") String lastName,
        String email,
        String phone,
        String address
) {}
