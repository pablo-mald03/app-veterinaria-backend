package com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Provider summary response dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderSummaryResponseDto {
    private Long id;
    private String name;
    private String nit;
}