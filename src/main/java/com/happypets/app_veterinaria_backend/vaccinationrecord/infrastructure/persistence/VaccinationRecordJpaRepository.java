package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationRecordJpaRepository extends JpaRepository<VaccinationRecordEntity, Long> {

    long countByIdCardAndIdTreatment(Long idCard, Long idTreatment);

    List<VaccinationRecordEntity> findByIdCardOrderByApplicationNumberAsc(Long idCard);

    /* Última aplicación de cada tratamiento de cada carnet cuya próxima fecha ya llegó */
    @Query("""
            SELECT r FROM VaccinationRecordEntity r
            WHERE r.nextDoseDate <= :until
              AND r.applicationNumber = (
                  SELECT MAX(r2.applicationNumber) FROM VaccinationRecordEntity r2
                  WHERE r2.idCard = r.idCard AND r2.idTreatment = r.idTreatment)
            """)
    List<VaccinationRecordEntity> findPending(@Param("until") LocalDate until);
}