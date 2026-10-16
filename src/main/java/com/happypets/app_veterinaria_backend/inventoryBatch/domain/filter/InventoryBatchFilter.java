package com.happypets.app_veterinaria_backend.inventoryBatch.domain.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Principal inventory batch filter
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBatchFilter {
    private Long productId;
    private Boolean status;
    private Boolean onlyAvailable;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}