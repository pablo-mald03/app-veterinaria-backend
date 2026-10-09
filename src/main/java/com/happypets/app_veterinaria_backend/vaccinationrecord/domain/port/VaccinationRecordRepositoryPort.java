package com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port;

import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationRecordRepositoryPort {

    VaccinationRecord save(VaccinationRecord record);

    List<VaccinationRecord> findByIdCard(Long idCard);

    int countByIdCardAndIdVaccine(Long idCard, Long idVaccine);

    List<VaccinationRecord> findPending(LocalDate until);
}
