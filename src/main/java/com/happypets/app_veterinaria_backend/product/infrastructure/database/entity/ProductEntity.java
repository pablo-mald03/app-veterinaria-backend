package com.happypets.app_veterinaria_backend.product.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.category.infrastructure.database.entity.CategoryEntity;
import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Principal products entity representation at persistency layer
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class ProductEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, unique = true)
    private String sku;

    @Column(length = 120)
    private String name;
    private String description;

    @Column(length = 120)
    private String brand;

    private long minimumStock;
    private BigDecimal price;

    @Column(nullable = false)
    private int profitPercentage;

    @Column(length = 150)
    private String provider;

    private boolean status = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
