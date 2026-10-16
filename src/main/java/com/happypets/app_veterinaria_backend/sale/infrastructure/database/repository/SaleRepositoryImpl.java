package com.happypets.app_veterinaria_backend.sale.infrastructure.database.repository;


import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.domain.filter.SaleFilter;
import com.happypets.app_veterinaria_backend.sale.domain.port.SaleRepositoryPort;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleDetailEntity;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleEntity;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.mapper.SaleEntityMapper;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.specifications.SaleSpecifications;
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
 * principal sale repository implementation
 *
 */
@Repository
@RequiredArgsConstructor
public class SaleRepositoryImpl implements SaleRepositoryPort {

    //Attributes
    private final QuerySaleRepository querySaleRepository;
    private final SaleEntityMapper saleMapper;

    private final QuerySaleDetailRepository querySaleDetailRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Principal method to save the new sale
     *
     */
    @Override
    @Transactional
    public Sale save(Sale domain) {
        SaleEntity entity = saleMapper.toEntity(domain);

        if (domain.getUserId() != null) {
            entity.setUser(entityManager.getReference(UserEntity.class, domain.getUserId()));
        }

        if (entity.getDetails() != null) {
            for (int i = 0; i < entity.getDetails().size(); i++) {
                SaleDetailEntity detail = entity.getDetails().get(i);
                Long productId = domain.getDetails().get(i).getProductId();

                detail.setSale(entity);
                detail.setProduct(entityManager.getReference(ProductEntity.class, productId));
            }
        }

        SaleEntity saved = querySaleRepository.saveAndFlush(entity);
        return findById(saved.getId()).orElseGet(() -> saleMapper.toDomain(saved));
    }

    /**
     * Principal method to find sale by id
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Sale> findById(Long id) {
        Specification<SaleEntity> spec = Specification.where(SaleSpecifications.fetchFullGraph())
                .and((root, query, cb) -> cb.equal(root.get("id"), id));

        return querySaleRepository.findOne(spec).map(saleMapper::toDomain);
    }

    /**
     * Principal method to find by bill number sale
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Sale> findByBillNumber(String billNumber) {
        Specification<SaleEntity> spec = Specification.where(SaleSpecifications.fetchFullGraph())
                .and((root, query, cb) -> cb.equal(root.get("billNumber"), billNumber));

        return querySaleRepository.findOne(spec).map(saleMapper::toDomain);
    }

    /**
     * Principal method to verify the bill number sale
     *
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByBillNumber(String billNumber) {
        return querySaleRepository.existsByBillNumber(billNumber);
    }

    /**
     * Principal method to find all sales without details
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<Sale> findAll(SaleFilter filter, PaginationQuery paginationQuery) {
        Pageable pageable = createPageable(paginationQuery);
        Specification<SaleEntity> spec = buildFilterSpecification(filter);

        Page<SaleEntity> page = querySaleRepository.findAll(spec, pageable);
        List<Sale> content = page.getContent().stream()
                .map(saleMapper::toDomainWithoutDetails)
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
     * Principal method to find all sales with details
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<Sale> findAllWithDetails(SaleFilter filter, PaginationQuery paginationQuery) {
        Pageable pageable = createPageable(paginationQuery);
        Specification<SaleEntity> spec = Specification.where(SaleSpecifications.fetchFullGraph())
                .and(buildFilterSpecification(filter));

        Page<SaleEntity> page = querySaleRepository.findAll(spec, pageable);
        List<Sale> content = page.getContent().stream()
                .map(saleMapper::toDomain)
                .toList();

        return new PaginationResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long getNextSaleSequence() {
        return querySaleRepository.getNextSequence();
    }


    /**
     * Pagination helper
     *
     */
    private Pageable createPageable(PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "date";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));
        return PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);
    }

    /**
     * Filter builder method
     *
     */
    private Specification<SaleEntity> buildFilterSpecification(SaleFilter filter) {
        return Specification.where(SaleSpecifications.hasBillNumber(filter.getBillNumber()))
                .and(SaleSpecifications.hasUserId(filter.getUserId()))
                .and(SaleSpecifications.hasStatus(filter.getStatus()))
                .and(SaleSpecifications.createdBetween(filter.getDateFrom(), filter.getDateTo()));
    }
}