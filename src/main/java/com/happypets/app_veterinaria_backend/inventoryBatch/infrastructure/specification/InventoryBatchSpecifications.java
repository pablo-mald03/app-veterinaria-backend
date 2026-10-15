package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.specification;

import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity.InventoryBatchEntity;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Inventory batch specifications filter
 *
 */
public class InventoryBatchSpecifications {
    /**
     * Specification to fetch full graph (product and purchaseDetail with purchase)
     *
     */
    public static Specification<InventoryBatchEntity> fetchFullGraph() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType()) || long.class.equals(query.getResultType()) || boolean.class.equals(query.getResultType())) {
                return null;
            }
            root.fetch("product", JoinType.LEFT);
            Fetch<?, ?> pdFetch = root.fetch("purchaseDetail", JoinType.LEFT);
            pdFetch.fetch("purchase", JoinType.LEFT);
            query.distinct(true);
            return null;
        };
    }

    /**
     * Specification to verify if the inventory has product id
     *
     */
    public static Specification<InventoryBatchEntity> hasProductId(Long productId) {
        return (root, query, cb) -> productId == null ? null : cb.equal(root.get("product").get("id"), productId);
    }

    /**
     * Filter to find by status
     *
     */
    public static Specification<InventoryBatchEntity> hasStatus(Boolean status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    /**
     * Specification to verify if the inventory is available
     *
     */
    public static Specification<InventoryBatchEntity> isAvailable() {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("status"), true),
                cb.greaterThan(root.get("availableQuantity"), 0L)
        );
    }

    /**
     * Filter to find only by stock
     *
     */
    public static Specification<InventoryBatchEntity> onlyWithStock(Boolean onlyAvailable) {
        return (root, query, cb) -> (onlyAvailable != null && onlyAvailable)
                ? cb.greaterThan(root.get("availableQuantity"), 0L)
                : null;
    }

    /**
     * Filter to find between dates
     *
     */
    public static Specification<InventoryBatchEntity> entryDateBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, cb) -> {
            if (dateFrom != null && dateTo != null) {
                return cb.between(root.get("entryDate"), dateFrom, dateTo);
            } else if (dateFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("entryDate"), dateFrom);
            } else if (dateTo != null) {
                return cb.lessThanOrEqualTo(root.get("entryDate"), dateTo);
            }
            return null;
        };
    }
}