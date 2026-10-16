package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseDetailEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Principal inventory persistency layer entity
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_batch")
public class InventoryBatchEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal unitaryCost;
    private int profitPercentage;

    private long availableQuantity;
    private long originalQuantity;
    private LocalDateTime entryDate;

    private boolean status = true;

    /*One product can be registered at many inventory batch*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_detail_id")
    private PurchaseDetailEntity purchaseDetail;

    //Hook for default time
    @PrePersist
    public void prePersist() {
        if (this.entryDate == null) {
            this.entryDate = LocalDateTime.now(ZoneId.of("America/Guatemala"));
        }
    }
}
