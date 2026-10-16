package com.happypets.app_veterinaria_backend.sale.infrastructure.database.specifications;

import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleStatus;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleEntity;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * Principal sale specification filters
 *
 */
public class SaleSpecifications {

    /**
     * Principal fetch full graph for sale (client, user, details and products)
     *
     */
    public static Specification<SaleEntity> fetchFullGraph() {
        return (root, query, cb) -> {
            if (Long.class.equals(query.getResultType()) || long.class.equals(query.getResultType()) || boolean.class.equals(query.getResultType())) {
                return null;
            }
            root.fetch("user", JoinType.LEFT);
            Fetch<?, ?> detailsFetch = root.fetch("details", JoinType.LEFT);
            detailsFetch.fetch("product", JoinType.LEFT);
            query.distinct(true);
            return null;
        };
    }

    /**
     * Filter by bill number (Approximate / Case-insensitive search)
     *
     */
    public static Specification<SaleEntity> hasBillNumber(String billNumber) {
        return (root, query, cb) -> (billNumber == null || billNumber.isBlank())
                ? null
                : cb.like(cb.lower(root.get("billNumber")), "%" + billNumber.toLowerCase().trim() + "%");
    }

    /**
     * Filter by user id
     *
     */
    public static Specification<SaleEntity> hasUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    /**
     * Filter by status
     *
     */
    public static Specification<SaleEntity> hasStatus(SaleStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    /**
     * Filter by created at date
     *
     */
    public static Specification<SaleEntity> createdBetween(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, cb) -> {
            if (dateFrom != null && dateTo != null) {
                return cb.between(root.get("date"), dateFrom, dateTo);
            } else if (dateFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("date"), dateFrom);
            } else if (dateTo != null) {
                return cb.lessThanOrEqualTo(root.get("date"), dateTo);
            }
            return null;
        };
    }
}