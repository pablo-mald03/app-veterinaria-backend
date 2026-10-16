package com.happypets.app_veterinaria_backend.report.domain.port;

import com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingVaccineRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepositoryPort {

    List<AttendedPetRecord> findAttendedPets(LocalDate from, LocalDate to);

    List<TreatmentFrequency> findFrequentTreatments(LocalDate from, LocalDate to);

    List<PendingVaccineRecord> findPendingVaccines(LocalDate until);
}
