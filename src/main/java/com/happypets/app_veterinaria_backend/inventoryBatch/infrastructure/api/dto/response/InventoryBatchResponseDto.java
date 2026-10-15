package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductSummaryDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.PurchaseDetailSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal inventory batch response dto
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBatchResponseDto {
    private Long id;
    private BigDecimal unitaryCost;
    private long availableQuantity;
    private int profitPercentage;
    private long originalQuantity;
    private LocalDateTime entryDate;
    private boolean status;
    private ProductSummaryDto product;
    private PurchaseDetailSummaryDto purchaseDetail;
}