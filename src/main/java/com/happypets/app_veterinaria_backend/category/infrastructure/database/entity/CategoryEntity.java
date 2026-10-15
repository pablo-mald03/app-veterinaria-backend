package com.happypets.app_veterinaria_backend.category.infrastructure.database.entity;

import com.happypets.app_veterinaria_backend.common.infrastructure.entity.AuditableEntity;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Principal categories entity representation at persistency layer
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "categories")
public class CategoryEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 120)
    private String name;
    @Column(length = 120)
    private String alias;
    private String description;

    private boolean status = true;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();
}
