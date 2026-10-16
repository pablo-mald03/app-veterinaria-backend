package com.happypets.app_veterinaria_backend.sale.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal sale summary domain model
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleSummary {
    private Long id;
    private String billNumber;
    private LocalDateTime date;
    private BigDecimal total;
}