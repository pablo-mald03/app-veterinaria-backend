package com.happypets.app_veterinaria_backend.purchase.infrastructure.database.repository;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.filter.PurchaseFilter;
import com.happypets.app_veterinaria_backend.purchase.domain.port.PurchaseRepositoryPort;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseDetailEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.mapper.PurchaseEntityMapper;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.specification.PurchaseSpecifications;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Principal purchase repository implementation
 *
 */
@Repository
@RequiredArgsConstructor
public class PurchaseRepositoryImpl implements PurchaseRepositoryPort {

    //Attributes
    private final QueryPurchaseRepository queryPurchaseRepository;
    private final PurchaseEntityMapper purchaseMapper;

    private final QueryPurchaseDetailRepository queryPurchaseDetailRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Principal method to save a purchase
     *
     */
    @Override
    @Transactional
    public Purchase save(Purchase domain) {
        PurchaseEntity entity = purchaseMapper.toEntity(domain);

        if (domain.getUserId() != null) {
            entity.setUser(entityManager.getReference(UserEntity.class, domain.getUserId()));
        }

        if (entity.getDetails() != null) {
            for (int i = 0; i < entity.getDetails().size(); i++) {
                PurchaseDetailEntity detail = entity.getDetails().get(i);
                Long productId = domain.getDetails().get(i).getProductId();

                detail.setPurchase(entity);
                detail.setProduct(entityManager.getReference(ProductEntity.class, productId));
            }
        }

        PurchaseEntity saved = queryPurchaseRepository.save(entity);
        return purchaseMapper.toDomain(saved);
    }

    /**
     * Method to update the description for the sale
     *
     */
    @Override
    @Transactional
    public void updateDescription(Long id, String description) {
        PurchaseEntity entity = queryPurchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));
        entity.setDescription(description);
        queryPurchaseRepository.save(entity);
    }

    /**
     * Method to find the sale by id
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> findById(Long id) {
        Specification<PurchaseEntity> spec = Specification.where(PurchaseSpecifications.fetchFullGraph())
                .and((root, query, cb) -> cb.equal(root.get("id"), id));

        return queryPurchaseRepository.findOne(spec).map(purchaseMapper::toDomain);
    }

    /**
     * Method to find all purchases
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<Purchase> findAll(PurchaseFilter filter, PaginationQuery paginationQuery) {
        Pageable pageable = createPageable(paginationQuery);
        Specification<PurchaseEntity> spec = Specification.where(PurchaseSpecifications.fetchFullGraph())
                .and(buildFilterSpecification(filter));

        Page<PurchaseEntity> page = queryPurchaseRepository.findAll(spec, pageable);
        List<Purchase> content = page.getContent().stream()
                .map(purchaseMapper::toDomainWithoutDetails)
                .toList();

        return new PaginationResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    /**
     * Find all without details
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<Purchase> findAllWithDetails(PurchaseFilter filter, PaginationQuery paginationQuery) {
        Pageable pageable = createPageable(paginationQuery);
        Specification<PurchaseEntity> spec = Specification.where(PurchaseSpecifications.fetchFullGraph())
                .and(buildFilterSpecification(filter));

        Page<PurchaseEntity> page = queryPurchaseRepository.findAll(spec, pageable);
        List<Purchase> content = page.getContent().stream()
                .map(purchaseMapper::toDomain)
                .toList();

        return new PaginationResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    /**
     * Method to verify if the purchase already exists
     *
     */
    @Override
    public boolean existsById(Long id) {
        return queryPurchaseRepository.existsById(id);
    }

    /**
     * Pagination determination helper
     *
     */
    private Pageable createPageable(PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "id";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        return PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);
    }

    /**
     * Filters builder
     */
    private Specification<PurchaseEntity> buildFilterSpecification(PurchaseFilter filter) {
        return Specification.where(PurchaseSpecifications.hasUserId(filter.getUserId()))
                .and(PurchaseSpecifications.hasStatus(filter.getStatus()))
                .and(PurchaseSpecifications.createdBetween(filter.getDateFrom(), filter.getDateTo()));
    }
}