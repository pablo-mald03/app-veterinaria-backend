package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity.InventoryBatchEntity;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.filter.InventoryMovementFilter;
import com.happypets.app_veterinaria_backend.inventoryMovement.domain.port.InventoryMovementRepositoryPort;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.entity.InventoryMovementEntity;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.mapper.InventoryMovementEntityMapper;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.specification.InventoryMovementSpecifications;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseEntity;
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
 * Principal inventory movement register
 *
 */
@Repository
@RequiredArgsConstructor
public class InventoryMovementRepositoryImpl implements InventoryMovementRepositoryPort {

    //Attributes
    private final QueryInventoryMovementRepository queryInventoryMovementRepository;
    private final InventoryMovementEntityMapper movementMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Method to register a new inventory movement
     *
     */
    @Override
    @Transactional
    public InventoryMovement save(InventoryMovement domain) {
        InventoryMovementEntity entity = movementMapper.toEntity(domain);

        if (domain.getProductId() != null) {
            entity.setProduct(entityManager.getReference(ProductEntity.class, domain.getProductId()));
        }
        if (domain.getBatchId() != null) {
            entity.setBatch(entityManager.getReference(InventoryBatchEntity.class, domain.getBatchId()));
        }
        if (domain.getPurchaseId() != null) {
            entity.setPurchase(entityManager.getReference(PurchaseEntity.class, domain.getPurchaseId()));
        }
     /*   if (domain.getSaleId() != null) {
            entity.setSale(entityManager.getReference(SaleEntity.class, domain.getSaleId()));
        }*/

        InventoryMovementEntity saved = queryInventoryMovementRepository.save(entity);
        return movementMapper.toDomain(saved);
    }

    /**
     * Method to find movement by id
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryMovement> findById(Long id) {
        Specification<InventoryMovementEntity> spec = Specification.where(InventoryMovementSpecifications.fetchFullGraph())
                .and((root, query, cb) -> cb.equal(root.get("id"), id));

        return queryInventoryMovementRepository.findOne(spec).map(movementMapper::toDomain);
    }

    /**
     * Method to find all movements
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<InventoryMovement> findAll(InventoryMovementFilter filter, PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "date";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Specification<InventoryMovementEntity> spec = Specification.where(InventoryMovementSpecifications.fetchFullGraph())
                .and(InventoryMovementSpecifications.hasProductId(filter.getProductId()))
                .and(InventoryMovementSpecifications.hasBatchId(filter.getBatchId()))
                .and(InventoryMovementSpecifications.hasPurchaseId(filter.getPurchaseId()))
//                .and(InventoryMovementSpecifications.hasSaleId(filter.getSaleId()))
                .and(InventoryMovementSpecifications.hasType(filter.getType()))
                .and(InventoryMovementSpecifications.createdBetween(filter.getDateFrom(), filter.getDateTo()));

        Page<InventoryMovementEntity> page = queryInventoryMovementRepository.findAll(spec, pageable);
        List<InventoryMovement> content = page.getContent().stream().map(movementMapper::toDomain).toList();

        return new PaginationResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }


    /**
     * Method to find movement history of a specific product paginated
     *
     */
 /*   @Override
    @Transactional(readOnly = true)
    public PaginationResult<ProductMovementHistoryDetail> findMovementHistoryByProductId(Long productId, PaginationQuery paginationQuery) {
        Pageable pageable = buildPageable(paginationQuery, "date");

        Page<ProductMovementHistoryDetail> page = queryInventoryMovementRepository.findMovementHistoryByProductId(productId, pageable);

        return new PaginationResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }*/

    /**
     * Helper method to create Pageable instance with fallback sort
     *
     */
    private Pageable buildPageable(PaginationQuery paginationQuery, String defaultSortBy) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : defaultSortBy;

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        return PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);
    }
}