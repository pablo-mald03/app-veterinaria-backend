package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.repository;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.LowStockProductSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.ProductStockSummary;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.filter.InventoryBatchFilter;
import com.happypets.app_veterinaria_backend.inventoryBatch.domain.port.InventoryBatchRepositoryPort;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity.InventoryBatchEntity;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.mapper.InventoryBatchEntityMapper;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.specification.InventoryBatchSpecifications;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseDetailEntity;
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
import java.util.Map;
import java.util.Optional;

/**
 * Principal inventory batch repository
 *
 */
@Repository
@RequiredArgsConstructor
public class InventoryBatchRepositoryImpl implements InventoryBatchRepositoryPort {

    private final QueryInventoryBatchRepository queryInventoryBatchRepository;
    private final InventoryBatchEntityMapper batchMapper;
    /**
     * Allowed sort fields for the stock summary (request field -> ProductEntity property)
     */
    private static final Map<String, String> SUMMARY_SORT_FIELDS = Map.of("productName", "name", "name", "name", "productSku", "sku",
            "sku", "sku", "minimumStock", "minimumStock");

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Principal method to save the inventory batch
     *
     */
    @Override
    @Transactional
    public InventoryBatch save(InventoryBatch domain) {
        InventoryBatchEntity entity = batchMapper.toEntity(domain);

        if (domain.getProductId() != null) {
            entity.setProduct(entityManager.getReference(ProductEntity.class, domain.getProductId()));
        }
        if (domain.getPurchaseDetailId() != null) {
            entity.setPurchaseDetail(entityManager.getReference(PurchaseDetailEntity.class, domain.getPurchaseDetailId()));
        }

        InventoryBatchEntity saved = queryInventoryBatchRepository.save(entity);
        return batchMapper.toDomain(saved);
    }

    /**
     * Method to find batch by id
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryBatch> findById(Long id) {
        Specification<InventoryBatchEntity> spec = Specification.where(InventoryBatchSpecifications.fetchFullGraph())
                .and((root, query, cb) -> cb.equal(root.get("id"), id));

        return queryInventoryBatchRepository.findOne(spec).map(batchMapper::toDomain);
    }

    /**
     * Find batches UEPS (LIFO).
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventoryBatch> findAvailableBatchesByProductIdUEPS(Long productId) {
        Specification<InventoryBatchEntity> spec = Specification.where(InventoryBatchSpecifications.hasProductId(productId))
                .and(InventoryBatchSpecifications.isAvailable());

        Sort uepsSort = Sort.by(Sort.Direction.DESC, "entryDate").and(Sort.by(Sort.Direction.DESC, "id"));

        return queryInventoryBatchRepository.findAll(spec, uepsSort).stream()
                .map(batchMapper::toDomain)
                .toList();
    }

    /**
     * Method to find available batch product
     *
     */
    @Override
    @Transactional
    public List<InventoryBatch> findAvailableBatchesByProductIdUEPSWithLock(Long productId) {
        return queryInventoryBatchRepository.findAvailableBatchesWithLock(productId).stream()
                .map(batchMapper::toDomain)
                .toList();
    }

    /**
     * Method to verify the aviable stock
     *
     */
    @Override
    @Transactional(readOnly = true)
    public long getTotalAvailableStock(Long productId) {
        return queryInventoryBatchRepository.getTotalAvailableStockByProductId(productId);
    }

    /**
     * Update available quantity method (manual movement)
     *
     */
    @Override
    @Transactional
    public void updateAvailableQuantity(Long batchId, long newAvailableQuantity, boolean status) {
        InventoryBatchEntity entity = queryInventoryBatchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Lote no encontrado con ID: " + batchId));

        entity.setAvailableQuantity(newAvailableQuantity);
        entity.setStatus(status);
        queryInventoryBatchRepository.save(entity);
    }

    /**
     * Get all with filter method
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<InventoryBatch> findAll(InventoryBatchFilter filter, PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "entryDate";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Specification<InventoryBatchEntity> spec = Specification.where(InventoryBatchSpecifications.fetchFullGraph())
                .and(InventoryBatchSpecifications.hasProductId(filter.getProductId()))
                .and(InventoryBatchSpecifications.hasStatus(filter.getStatus()))
                .and(InventoryBatchSpecifications.onlyWithStock(filter.getOnlyAvailable()))
                .and(InventoryBatchSpecifications.entryDateBetween(filter.getDateFrom(), filter.getDateTo()));

        Page<InventoryBatchEntity> page = queryInventoryBatchRepository.findAll(spec, pageable);
        List<InventoryBatch> content = page.getContent().stream().map(batchMapper::toDomain).toList();

        return new PaginationResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    /**
     * Method to find products with low stock paginated
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<LowStockProductSummary> findLowStockProducts(PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = paginationQuery.getSortBy();

        if (sortBy == null || sortBy.isBlank() || "currentStock".equalsIgnoreCase(sortBy)) {
            sortBy = "name";
        }

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Page<LowStockProductSummary> page = queryInventoryBatchRepository.findLowStockProducts(pageable);

        return new PaginationResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    /**
     * Get all stock summary method
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<ProductStockSummary> findAllSummary(PaginationQuery paginationQuery) {

        Sort.Direction direction = "DESC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        String requestedSort = paginationQuery.getSortBy();
        String sortBy = requestedSort != null
                ? SUMMARY_SORT_FIELDS.getOrDefault(requestedSort, "name")
                : "name";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Page<ProductStockSummary> page = queryInventoryBatchRepository.findProductStockSummaries(pageable);

        return new PaginationResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    /**
     * Helper method to create Pageable instance with fallback sort
     *
     */
    private Pageable buildPageable(PaginationQuery paginationQuery, String defaultSortBy) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection()) ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : defaultSortBy;

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        return PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);
    }

}