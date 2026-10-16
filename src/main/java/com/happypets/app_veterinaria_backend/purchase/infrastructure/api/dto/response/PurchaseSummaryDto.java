package com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal purchase summary dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSummaryDto {
    private Long id;
    private String description;
    private LocalDateTime date;
    private BigDecimal total;
}