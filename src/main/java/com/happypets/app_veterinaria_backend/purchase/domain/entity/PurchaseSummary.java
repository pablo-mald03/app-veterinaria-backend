package com.happypets.app_veterinaria_backend.purchase.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Purchase summary domain model
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSummary {
    private Long id;
    private String description;
    private LocalDateTime date;
    private BigDecimal total;
}