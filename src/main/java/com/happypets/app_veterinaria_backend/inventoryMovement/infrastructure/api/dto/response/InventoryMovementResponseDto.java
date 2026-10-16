package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.api.dto.response;

import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.api.dto.response.BatchSummaryDto;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import com.happypets.app_veterinaria_backend.product.infrastructure.api.dto.response.ProductSummaryDto;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.api.dto.response.PurchaseSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Principal inventory movement response
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMovementResponseDto {
    private Long id;
    private MovementType type;
    private long quantity;
    private BigDecimal unitCost;
    private LocalDateTime date;
    private String origin;
    private ProductSummaryDto product;
    private BatchSummaryDto batch;
    private PurchaseSummaryDto purchase;
    //private SaleSummaryDto sale;
}