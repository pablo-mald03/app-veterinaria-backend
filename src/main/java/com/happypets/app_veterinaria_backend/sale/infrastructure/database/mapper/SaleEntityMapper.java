package com.happypets.app_veterinaria_backend.sale.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.domain.entity.SaleDetail;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleDetailEntity;
import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleEntity;
import com.happypets.app_veterinaria_backend.user.domain.entity.UserSummary;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.mapstruct.*;

/**
 * Principal mapper class for the sale persistence and domain layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SaleEntityMapper {
    /**
     * Mapper of entity to domain sale
     */
    @Mapping(target = "userId", source = "user.id")
    Sale toDomain(SaleEntity entity);

    /**
     * Mapper of entity to domain without sale details
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "details", ignore = true)
    Sale toDomainWithoutDetails(SaleEntity entity);

    /**
     * Mapper of entity to domain with sale details
     */
    @Mapping(target = "productId", source = "product.id")
    SaleDetail toDomainDetail(SaleDetailEntity entity);


    /**
     * User summary mapping
     */
    UserSummary toUserSummary(UserEntity entity);

    /**
     * Product summary mapping
     */
    ProductSummary toProductSummary(ProductEntity entity);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "details", source = "details")
    SaleEntity toEntity(Sale domain);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "sale", ignore = true)
    SaleDetailEntity toEntityDetail(SaleDetail domain);

    @AfterMapping
    default void linkDetails(@MappingTarget SaleEntity saleEntity) {
        if (saleEntity.getDetails() != null) {
            saleEntity.getDetails().forEach(detail -> detail.setSale(saleEntity));
        }
    }
}