package com.happypets.app_veterinaria_backend.vaccinationrecord.application.query;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPendingVaccinationRecordsHandler {

    private final VaccinationRecordRepositoryPort recordPort;

    public List<VaccinationRecord> execute(int days) {
        return recordPort.findPending(LocalDate.now().plusDays(days));
    }
}
