package com.happypets.app_veterinaria_backend.purchase.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.purchase.infrastructure.database.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Principal purchase query repository entity
 *
 */
@Repository
public interface QueryPurchaseRepository extends JpaRepository<PurchaseEntity, Long>, JpaSpecificationExecutor<PurchaseEntity> {

}