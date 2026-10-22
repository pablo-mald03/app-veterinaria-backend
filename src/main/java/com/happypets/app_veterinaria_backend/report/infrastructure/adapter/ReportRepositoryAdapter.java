package com.happypets.app_veterinaria_backend.report.infrastructure.adapter;

import com.happypets.app_veterinaria_backend.report.domain.model.*;
import com.happypets.app_veterinaria_backend.report.domain.port.ReportRepositoryPort;
import com.happypets.app_veterinaria_backend.report.infrastructure.persistence.ReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportRepositoryAdapter implements ReportRepositoryPort {

    private final ReportJpaRepository reportJpaRepository;

    @Override
    public List<AttendedPetRecord> findAttendedPets(LocalDate from, LocalDate to) {
        return reportJpaRepository.findAttendedPets(AppointmentStatus.ATENDIDA, from, to);
    }

    @Override
    public List<TreatmentFrequency> findFrequentTreatments(LocalDate from, LocalDate to) {
        return reportJpaRepository.findFrequentTreatments(AppointmentStatus.ATENDIDA, from, to);
    }

    @Override
    public List<PendingTreatmentRecord> findPendingVaccines(LocalDate until) {
        return reportJpaRepository.findPendingVaccines(until);
    }
}
