package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity.InventoryBatchEntity;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Principal inventory movement persistency layer entity
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_movement")
public class InventoryMovementEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    /*
     * IN:for purchase,
     * OUT: for sale*/
    private MovementType type;

    private long quantity;
    private BigDecimal unitCost;

    private LocalDateTime date;

    private String origin;

    /*Product relationship (one product has many movements)*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    /*Batch inventory relationship*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private InventoryBatchEntity batch;

    /*Sale relationship (nullable)*/
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private SaleEntity sale;
*/

    /*Purchase relationship (nullable)*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private PurchaseEntity purchase;

    //Hook for default time
    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now(ZoneId.of("America/Guatemala"));
        }
    }
}
