package com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.inventoryBatch.domain.entity.InventoryBatch;
import com.happypets.app_veterinaria_backend.inventoryBatch.infrastructure.database.entity.InventoryBatchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Principal mapper class for the inventory batch persistence and domain layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InventoryBatchEntityMapper {

    /**
     * Mapper from entity to domain
     *
     */
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "purchaseDetailId", source = "purchaseDetail.id")
    @Mapping(target = "purchaseDetail.purchaseId", source = "purchaseDetail.purchase.id")
    InventoryBatch toDomain(InventoryBatchEntity entity);

    /**
     * Mapper from domain to entity
     *
     */
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "purchaseDetail", ignore = true)
    InventoryBatchEntity toEntity(InventoryBatch domain);
}