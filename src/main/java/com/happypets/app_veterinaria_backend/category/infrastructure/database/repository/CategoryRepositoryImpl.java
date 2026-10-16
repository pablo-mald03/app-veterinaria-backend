package com.happypets.app_veterinaria_backend.category.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.domain.exception.CategoryNotFoundException;
import com.happypets.app_veterinaria_backend.category.domain.filter.CategoryFilter;
import com.happypets.app_veterinaria_backend.category.domain.port.CategoryRepositoryPort;
import com.happypets.app_veterinaria_backend.category.infrastructure.database.entity.CategoryEntity;
import com.happypets.app_veterinaria_backend.category.infrastructure.database.mapper.CategoryEntityMapper;
import com.happypets.app_veterinaria_backend.category.infrastructure.database.specification.CategorySpecifications;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
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

public class CategoryRepositoryImpl implements CategoryRepositoryPort {

    private final QueryCategoryRepository queryCategoryRepository;
    private final CategoryEntityMapper categoryMapper;

    /**
     * Method to find all categories
     *
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResult<Category> findAll(CategoryFilter filter, PaginationQuery paginationQuery) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(paginationQuery.getDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String sortBy = (paginationQuery.getSortBy() != null && !paginationQuery.getSortBy().isBlank())
                ? paginationQuery.getSortBy()
                : "id";

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(direction, "id"));

        Pageable pageable = PageRequest.of(paginationQuery.getPage(), paginationQuery.getSize(), sort);

        Specification<CategoryEntity> spec = Specification.where(CategorySpecifications.hasStatus(filter.getStatus()));

        Page<CategoryEntity> page = queryCategoryRepository.findAll(spec, pageable);
        List<Category> content = page.getContent().stream().map(categoryMapper::toDomain).toList();

        return new PaginationResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    /**
     * Method to find category by id
     *
     */
    @Override
    public Optional<Category> findById(Long id) {
        return queryCategoryRepository.findById(id).map(categoryMapper::toDomain);
    }

    /**
     * Method to save the category
     *
     */
    @Override
    public Category save(Category domain) {
        CategoryEntity entity = categoryMapper.toEntity(domain);
        return categoryMapper.toDomain(queryCategoryRepository.save(entity));
    }

    /**
     * Method to update the category
     *
     */
    @Override
    public Category update(Category domain) {
        CategoryEntity entity = queryCategoryRepository.findById(domain.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria no encontrada: " + domain.getName()));

        entity.setName(domain.getName());
        entity.setAlias(domain.getAlias());
        entity.setDescription(domain.getDescription());

        return categoryMapper.toDomain(queryCategoryRepository.save(entity));
    }

    /**
     * Method to change the category status
     *
     */
    @Override
    public void changeStatus(Long id, boolean status) {
        int updated = queryCategoryRepository.changeStatus(id, status);
        if (updated == 0) {
            throw new CategoryNotFoundException("Categoria no encontrada: " + id);
        }
    }

    /**
     * Method to validate if the category alias exists
     *
     */
    @Override
    public boolean existsByAlias(String alias) {
        return queryCategoryRepository.existsByAlias(alias);
    }

}