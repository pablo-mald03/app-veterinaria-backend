package com.happypets.app_veterinaria_backend.sale.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Principal sale detail repository
 *
 */
@Repository
public interface QuerySaleDetailRepository extends JpaRepository<SaleDetailEntity, Long> {
}