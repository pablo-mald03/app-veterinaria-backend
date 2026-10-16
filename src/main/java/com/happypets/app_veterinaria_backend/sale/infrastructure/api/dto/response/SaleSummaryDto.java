package com.happypets.app_veterinaria_backend.sale.infrastructure.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal sale summary dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleSummaryDto {
    private Long id;
    private String billNumber;
    private LocalDateTime date;
    private BigDecimal total;
}