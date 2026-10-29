package com.happypets.app_veterinaria_backend.report.infrastructure.persistence;

import com.happypets.app_veterinaria_backend.appointment.infrastructure.database.entity.AppointmentJPAEntity;
import com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingTreatmentRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingVaccineRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReportJpaRepository extends JpaRepository<AppointmentJPAEntity, Long> {

    @Query("""
            SELECT new com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord(
                a.id, p.idPet, p.name, p.species, a.date, a.treatment, a.diagnosis, a.cost)
            FROM AppointmentJPAEntity a
            JOIN a.pet p
            WHERE a.status = :status
              AND a.date BETWEEN :from AND :to
            ORDER BY a.date, p.name
            """)
    List<AttendedPetRecord> findAttendedPets(@Param("status") String status,
                                             @Param("from") LocalDate from,
                                             @Param("to") LocalDate to);

    @Query("""
            SELECT new com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency(
                LOWER(TRIM(a.treatment)), COUNT(a), SUM(a.cost))
            FROM AppointmentJPAEntity a
            WHERE a.status = :status
              AND a.treatment IS NOT NULL
              AND a.date BETWEEN :from AND :to
            GROUP BY LOWER(TRIM(a.treatment))
            ORDER BY COUNT(a) DESC
            """)
    List<TreatmentFrequency> findFrequentTreatments(@Param("status") String status,
                                                    @Param("from") LocalDate from,
                                                    @Param("to") LocalDate to);

    @Query("""
        SELECT new com.happypets.app_veterinaria_backend.report.domain.model.PendingTreatmentRecord(
            p.idPet, p.name, p.species, t.name, r.applicationNumber, r.nextDoseDate)
        FROM VaccinationRecordEntity r
        JOIN VaccinationCardEntity c ON c.idCard = r.idCard
        JOIN PetEntity p ON p.idPet = c.idPet
        JOIN TreatmentEntity t ON t.idTreatment = r.idTreatment
        WHERE r.nextDoseDate IS NOT NULL
          AND r.nextDoseDate <= :until
          AND NOT EXISTS (
              SELECT 1 FROM VaccinationRecordEntity r2
              WHERE r2.idCard = r.idCard
                AND r2.idTreatment = r.idTreatment
                AND r2.applicationNumber > r.applicationNumber)
        ORDER BY r.nextDoseDate
        """)
    List<PendingTreatmentRecord> findPendingVaccines(@Param("until") LocalDate until);
}