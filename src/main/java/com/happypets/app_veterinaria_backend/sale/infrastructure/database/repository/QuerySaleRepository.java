package com.happypets.app_veterinaria_backend.sale.infrastructure.database.repository;

import com.happypets.app_veterinaria_backend.sale.infrastructure.database.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Principal query repository sale
 *
 */
@Repository
public interface QuerySaleRepository extends JpaRepository<SaleEntity, Long>, JpaSpecificationExecutor<SaleEntity> {

    @Query("SELECT COALESCE(MAX(s.id), 0) + 1 FROM SaleEntity s")
    long getNextSequence();

    boolean existsByBillNumber(String billNumber);

    Optional<SaleEntity> findByBillNumber(String billNumber);

}