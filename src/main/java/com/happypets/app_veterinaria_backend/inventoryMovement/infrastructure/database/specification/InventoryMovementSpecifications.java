package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.specification;


import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.MovementType;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.entity.InventoryMovementEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Inventory movement specifications filter
 *
 */
public class InventoryMovementSpecifications {


    /**
     * Specification for fetch all
     *
     */
    public static Specification<InventoryMovementEntity> fetchFullGraph() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType()) || boolean.class.equals(query.getResultType())) {
                return cb.conjunction();
            }
            root.fetch("product", JoinType.LEFT);
            root.fetch("batch", JoinType.LEFT);
            root.fetch("purchase", JoinType.LEFT);
            // root.fetch("sale", JoinType.LEFT);
            query.distinct(true);
            return cb.conjunction();
        };
    }


    /**
     * Filter to find by product id
     *
     */
    public static Specification<InventoryMovementEntity> hasProductId(Long productId) {
        return (root, query, cb) -> productId == null ? cb.conjunction() : cb.equal(root.get("product").get("id"), productId);
    }

    /**
     * Filter to find by batch id
     *
     */
    public static Specification<InventoryMovementEntity> hasBatchId(Long batchId) {
        return (root, query, cb) -> batchId == null ? cb.conjunction() : cb.equal(root.get("batch").get("id"), batchId);
    }

    /**
     * Filter to find by purchase id
     *
     */
    public static Specification<InventoryMovementEntity> hasPurchaseId(Long purchaseId) {
        return (root, query, cb) -> purchaseId == null ? cb.conjunction() : cb.equal(root.get("purchase").get("id"), purchaseId);
    }

    /**
     * Filter to find by sale id
     *
     */
    public static Specification<InventoryMovementEntity> hasSaleId(Long saleId) {
        return (root, query, cb) -> saleId == null ? cb.conjunction() : cb.equal(root.get("sale").get("id"), saleId);
    }

    /**
     * Filter to find by types
     *
     */
    public static Specification<InventoryMovementEntity> hasType(MovementType type) {
        return (root, query, cb) -> type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    /**
     * Filter to find between dates
     *
     */
    public static Specification<InventoryMovementEntity> createdBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, cb) -> {
            if (dateFrom != null && dateTo != null) {
                return cb.between(root.get("date"), dateFrom, dateTo);
            } else if (dateFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("date"), dateFrom);
            } else if (dateTo != null) {
                return cb.lessThanOrEqualTo(root.get("date"), dateTo);
            }
            return cb.conjunction();
        };
    }
}