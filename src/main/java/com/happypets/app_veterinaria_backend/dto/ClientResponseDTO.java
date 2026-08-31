package com.happypets.app_veterinaria_backend.dto;

public record ClientResponseDTO(
        Long id,
        String dpi,
        String firstName,
        String lastName,
        String phone,
        String address,
        String email
) {}
