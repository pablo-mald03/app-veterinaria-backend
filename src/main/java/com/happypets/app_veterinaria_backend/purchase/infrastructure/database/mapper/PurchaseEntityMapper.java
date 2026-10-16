package com.happypets.app_veterinaria_backend.purchase.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.product.domain.entity.ProductSummary;
import com.happypets.app_veterinaria_backend.product.infrastructure.database.entity.ProductEntity;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.PurchaseDetail;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseDetailEntity;
import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseEntity;
import com.happypets.app_veterinaria_backend.user.domain.entity.UserSummary;
import com.happypets.app_veterinaria_backend.user.infrastructure.database.entity.UserEntity;
import org.mapstruct.*;

/**
 * Principal mapper class for the purchase persistence and domain layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseEntityMapper {

    /**
     * Mapper from purchase entity to domain
     *
     */

    @Mapping(target = "userId", source = "user.id")
    Purchase toDomain(PurchaseEntity entity);

    /**
     * Mapper of purchase without details
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "details", ignore = true)
    Purchase toDomainWithoutDetails(PurchaseEntity entity);


    /**
     * Mapper from purchase detail entity to domain
     *
     */
    @Mapping(target = "productId", source = "product.id")
    PurchaseDetail toDomainDetail(PurchaseDetailEntity entity);


    /**
     * User summary mapping
     */
    UserSummary toUserSummary(UserEntity entity);


    /**
     * Product summary mapping
     */
    ProductSummary toProductSummary(ProductEntity entity);

    /**
     * Mapper from purchase domain to entity
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "details", source = "details")
    PurchaseEntity toEntity(Purchase domain);


    /**
     * Mapper from purchase detail domain to entity
     *
     */
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "purchase", ignore = true)
    PurchaseDetailEntity toEntityDetail(PurchaseDetail domain);

    /**
     * Mapper to load the linked details from purchase detail domain to entity
     *
     */
    @AfterMapping
    default void linkDetails(@MappingTarget PurchaseEntity purchaseEntity) {
        if (purchaseEntity.getDetails() != null) {
            purchaseEntity.getDetails().forEach(detail -> detail.setPurchase(purchaseEntity));
        }
    }
}