package com.happypets.app_veterinaria_backend.vaccinationrecord.application.query;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetVaccinationRecordsByCardHandler {

    private final VaccinationRecordRepositoryPort recordPort;

    public List<VaccinationRecord> execute(Long idCard) {
        return recordPort.findByIdCard(idCard);
    }
}
