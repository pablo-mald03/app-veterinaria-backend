package com.happypets.app_veterinaria_backend.sale.domain.filter;

import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Principal sale filter
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleFilter {
    private String billNumber;
    private Long customerId;
    private Long userId;
    private SaleStatus status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}