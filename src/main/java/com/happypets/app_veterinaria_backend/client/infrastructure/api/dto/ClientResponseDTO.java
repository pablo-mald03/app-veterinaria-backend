package com.happypets.app_veterinaria_backend.client.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String dpi;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
