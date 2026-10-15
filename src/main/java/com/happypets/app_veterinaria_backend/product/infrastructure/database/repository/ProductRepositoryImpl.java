package com.happypets.app_veterinaria_backend.product.infrastructure.database.repository;


import com.happypets.app_veterinaria_backend.category.infrastructure.database.entity.CategoryEntity;
import com.happypets.app_veterinaria_backend.category.infrastructure.database.mapper.CategoryEntityMapper;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.Product;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import com.happypets.app_veterinaria_backend.product.domain.exception.ProductNotFountException;
import com.happypets.app_veterinaria_backend.product.domain.filter.ProductFilter;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.mapper.ProductEntityMapper;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.specification.ProductSpecifications;
import jakarta.persistence.EntityManager;
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
 * Repository implementation
 *
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryPort {

    private final QueryProductRepository queryProductRepository;
    private final ProductEntityMapper productMapper;
    private final CategoryEntityMapper categoryMapper;
    private final EntityManager entityManager;

    /**
     * Method to update the product
     */
    @Override
    public Product update(Product domain) {
        ProductEntity entity = queryProductRepository.findById(domain.getId())
                .orElseThrow(() -> new ProductNotFountException("Producto no encontrado SKU: " + domain.getSku()));

        entity.setSku(domain.getSku());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setBrand(domain.getBrand());
        entity.setProvider(domain.getProvider());
        entity.setProfitPercentage(domain.getProfitPercentage());
        entity.setMinimumStock(domain.getMinimumStock());
        entity.setPrice(domain.getPrice());

        if (domain.getCategoryId() != null) {
            entity.setCategory(entityManager.getReference(CategoryEntity.class, domain.getCategoryId()));
        }

        return productMapper.toDomain(queryProductRepository.save(entity));
    }

    /**
     * Method to change the status
     */
    @Override
    public void changeStatus(Long id, boolean status) {
        int updated = queryProductRepository.changeStatus(id, status);
        if (updated == 0) {
            throw new ProductNotFountException("Producto no encontrado: " + id);
        }
    }

    /**
     * Method to reassign the category
     */
    @Override
    public Product reassignCategory(Long productId, Long categoryId) {
        ProductEntity entity = queryProductRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFountException("Producto no encontrado con ID: " + productId));
        entity.setCategory(entityManager.getReference(CategoryEntity.class, categoryId));
        return productMapper.toDomain(queryProductRepository.save(entity));
    }

    /**
     * Method to verify if the sku already exists
     */
    @Override
    public boolean existSku(String sku) {
        return queryProductRepository.existsBySku(sku.trim());
    }

    /**
     * Method to verify if the product exists
     */
    @Override
    public boolean existsById(Long id) {
        return queryProductRepository.existsById(id);
    }

    /**
     * Method to verify if a category is being used by any product
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCategoryId(Long categoryId) {
        return queryProductRepository.existsByCategoryId(categoryId);
    }

    /**
     * Method to find all
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<ProductDetail> findAll(ProductFilter filter, PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "id";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));

        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Specification<ProductEntity> spec = Specification
                .where(ProductSpecifications.priceFrom(filter.getPriceFrom()))
                .and(ProductSpecifications.priceTo(filter.getPriceTo()))
                .and(ProductSpecifications.hasCategory(filter.getCategoryId()))
                .and(ProductSpecifications.hasSku(filter.getSku()))
                .and(ProductSpecifications.hasProvider(filter.getProvider()))
                .and(ProductSpecifications.fetchRelations());

        Page<ProductEntity> page = queryProductRepository.findAll(spec, pageable);

        List<ProductDetail> content = page.getContent().stream()
                .map(this::toDetail)
                .toList();

        return new PaginationResult<>(content, page.getNumber(), page.getSize(),
                page.getTotalPages(), page.getTotalElements());
    }

    /**
     * Method to find by id
     */
    @Override
    public Optional<ProductDetail> findById(Long id) {
        Specification<ProductEntity> spec = Specification.where(ProductSpecifications.fetchRelations())
                .and((root, query, cb) -> cb.equal(root.get("id"), id));

        return queryProductRepository.findOne(spec).map(this::toDetail);
    }


    /**
     * Method to save the product
     */
    @Override
    public Product save(Product domain) {
        ProductEntity entity = productMapper.toEntity(domain);

        if (domain.getCategoryId() != null) {
            entity.setCategory(entityManager.getReference(CategoryEntity.class, domain.getCategoryId()));
        }

        ProductEntity savedEntity = queryProductRepository.save(entity);

        return productMapper.toDomain(savedEntity);
    }

    /**
     * Method to map to product detail
     */
    private ProductDetail toDetail(ProductEntity entity) {
        return new ProductDetail(
                productMapper.toDomain(entity),
                categoryMapper.toDomain(entity.getCategory())
        );
    }
}