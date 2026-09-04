package com.happypets.app_veterinaria_backend.client.application.dto;

public record ClientResponseDTO(
   Long id,
   String dpi,
   String firtsName,
   String lastName,
   String email,
   String phone,
   String address
) {}
