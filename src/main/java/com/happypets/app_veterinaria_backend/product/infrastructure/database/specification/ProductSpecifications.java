package com.happypets.app_veterinaria_backend.product.infrastructure.database.specification;

import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

/**
 * Principal filter class specifications for the product persistence layer
 *
 */
public class ProductSpecifications {

    /**
     * Fetch providers
     *
     */
    public static Specification<ProductEntity> fetchProviders() {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("providers", JoinType.LEFT);
                query.distinct(true);
            }
            return cb.conjunction();
        };
    }


    /**
     * Provider filter
     */
    public static Specification<ProductEntity> hasProvider(String provider) {
        return (root, query, cb) ->
                (provider == null || provider.isBlank())
                        ? null
                        : cb.like(cb.lower(root.get("provider")), "%" + provider.trim().toLowerCase() + "%");
    }

    /**
     * Price from filter
     *
     */
    public static Specification<ProductEntity> priceFrom(Long price) {
        return (root, query, cb) ->
                price == null ? null : cb.greaterThanOrEqualTo(root.get("price"), price);
    }

    /**
     * Price to filter
     *
     */
    public static Specification<ProductEntity> priceTo(Long price) {
        return (root, query, cb) ->
                price == null ? null : cb.lessThanOrEqualTo(root.get("price"), price);
    }

    /**
     * Category filter
     *
     */
    public static Specification<ProductEntity> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    /**
     * Sku filter (Approximate search)
     *
     *
     */
    public static Specification<ProductEntity> hasSku(String sku) {
        return (root, query, cb) ->
                (sku == null || sku.isBlank()) ? null : cb.like(cb.lower(root.get("sku")), "%" + sku.trim().toLowerCase() + "%");
    }

    /**
     * Fetch relations (because the fetch strategy for the product entity is LAZY)
     */
    public static Specification<ProductEntity> fetchRelations() {
        return (root, query, cb) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch("category", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }
}