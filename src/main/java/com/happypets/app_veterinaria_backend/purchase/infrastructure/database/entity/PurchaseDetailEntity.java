package com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Principal purchases persistency layer entity
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchases_detail")
public class PurchaseDetailEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long quantity;
    private BigDecimal unitPrice;
    private BigDecimal iva;
    private int profitPercentage;

    /* Relationship  with the purchase */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchases_id")
    private PurchaseEntity purchase;

    /* Relationship  with the product */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
