package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Principal expiring products response dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpiringProductResponseDto {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private LocalDateTime expirationDate;
}