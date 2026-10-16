package com.happypets.app_veterinaria_backend.product.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.product.domain.entity.Product;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Principal persistency mapper
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductEntityMapper {

    /**
     * Method to map from entity to domain
     *
     */
    @Mapping(target = "categoryId",
            expression = "java(entity.getCategory() != null ? entity.getCategory().getId() : null)")
    Product toDomain(ProductEntity entity);

    /**
     * Method to map from domain to entity
     *
     */
    @Mapping(target = "category", ignore = true)
    ProductEntity toEntity(Product domain);
    
}
