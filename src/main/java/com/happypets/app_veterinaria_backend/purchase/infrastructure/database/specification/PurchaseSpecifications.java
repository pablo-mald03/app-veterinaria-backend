package com.happypets.app_veterinaria_backend.purchase.infrastructure.database.specification;

import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseStatus;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseEntity;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Principal purchase specification
 *
 */
public class PurchaseSpecifications {

    /**
     * Join fetch filter
     */
    public static Specification<PurchaseEntity> fetchFullGraph() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType()) || long.class.equals(query.getResultType())) {
                return cb.conjunction();
            }
            root.fetch("user", JoinType.LEFT);
            Fetch<?, ?> detailsFetch = root.fetch("details", JoinType.LEFT);
            detailsFetch.fetch("product", JoinType.LEFT);
            query.distinct(true);
            return cb.conjunction();
        };
    }

    /**
     * Fetching with details
     *
     */
    public static Specification<PurchaseEntity> fetchDetails() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType()) || long.class.equals(query.getResultType())) {
                return null;
            }
            root.fetch("details", JoinType.LEFT);
            query.distinct(true);
            return null;
        };
    }

    /**
     * Fetching by user id
     *
     */
    public static Specification<PurchaseEntity> hasUserId(UUID userId) {
        return (root, query, cb) -> userId == null ? cb.conjunction() : cb.equal(root.get("user").get("id"), userId);
    }

    /**
     * Fetching by status
     *
     */
    public static Specification<PurchaseEntity> hasStatus(PurchaseStatus status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    /**
     * Fetching by range of dates
     *
     */
    public static Specification<PurchaseEntity> createdBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
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