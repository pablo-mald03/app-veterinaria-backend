package com.happypets.app_veterinaria_backend.purchase.domain.filter;

import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Principal purchase filter
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseFilter {
    private UUID userId;
    private PurchaseStatus status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}