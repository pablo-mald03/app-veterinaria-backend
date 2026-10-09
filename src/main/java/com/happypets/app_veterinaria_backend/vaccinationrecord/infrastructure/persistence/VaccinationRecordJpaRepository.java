package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationRecordJpaRepository extends JpaRepository<VaccinationRecordEntity, Long> {

    List<VaccinationRecordEntity> findByIdCardOrderByApplicationDateAsc(Long idCard);

    long countByIdCardAndIdVaccine(Long idCard, Long idVaccine);

    @Query("""
            SELECT r FROM VaccinationRecordEntity r
            WHERE r.nextDoseDate IS NOT NULL
              AND r.nextDoseDate <= :until
              AND NOT EXISTS (
                  SELECT 1 FROM VaccinationRecordEntity r2
                  WHERE r2.idCard = r.idCard
                    AND r2.idVaccine = r.idVaccine
                    AND r2.doseNumber > r.doseNumber)
            ORDER BY r.nextDoseDate
            """)
    List<VaccinationRecordEntity> findPending(@Param("until") LocalDate until);
}