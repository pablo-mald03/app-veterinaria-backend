package com.happypets.app_veterinaria_backend.category.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.category.domain.entity.Category;
import com.happypets.app_veterinaria_backend.category.infrastructure.database.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Principal persistency mapper
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryEntityMapper {

    /**
     * Method to map from entity to domain
     *
     */
    Category toDomain(CategoryEntity entity);


    /**
     * Method to map from domain to entity
     *
     */
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "status", defaultValue = "true")
    CategoryEntity toEntity(Category domain);

}
