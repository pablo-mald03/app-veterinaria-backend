package com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.mapper;

import com.happypets.app_veterinaria_backend.inventoryMovement.domain.entity.InventoryMovement;
import com.happypets.app_veterinaria_backend.inventoryMovement.infrastructure.database.entity.InventoryMovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Principal mapper class for the inventory movement persistence and domain layers
 *
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InventoryMovementEntityMapper {

    /**
     * Mapper from entity to domain
     *
     */
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "batchId", source = "batch.id")
    @Mapping(target = "purchaseId", source = "purchase.id")
    @Mapping(target = "saleId", source = "sale.id")
    InventoryMovement toDomain(InventoryMovementEntity entity);

    /**
     * Mapper from domain to entity
     *
     */
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "batch", ignore = true)
    @Mapping(target = "purchase", ignore = true)
    @Mapping(target = "sale", ignore = true)
    InventoryMovementEntity toEntity(InventoryMovement domain);
}