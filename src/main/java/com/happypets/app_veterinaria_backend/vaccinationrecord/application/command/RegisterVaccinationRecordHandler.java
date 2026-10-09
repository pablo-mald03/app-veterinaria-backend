package com.happypets.app_veterinaria_backend.vaccinationrecord.application.command;

import com.happypets.app_veterinaria_backend.vaccination.domain.exception.VaccineNotFoundException;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.exception.VaccinationCardNotFoundException;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.exception.VaccinationSchemeCompletedException;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterVaccinationRecordHandler {

    private final VaccinationRecordRepositoryPort recordPort;
    private final VaccinationCardRepositoryPort cardPort;
    private final VaccineRepository vaccinePort;

    @Transactional
    public VaccinationRecord execute(RegisterVaccinationRecordCommand command) {
        cardPort.findById(command.idCard())
                .orElseThrow(() -> new VaccinationCardNotFoundException(command.idCard()));

        Vaccine vaccine = vaccinePort.findById(command.idVaccine())
                .orElseThrow(() -> new VaccineNotFoundException(command.idVaccine()));

        int doseNumber = recordPort.countByIdCardAndIdVaccine(command.idCard(), command.idVaccine()) + 1;

        if (doseNumber > vaccine.getDosesRequired()) {
            throw new VaccinationSchemeCompletedException(command.idVaccine());
        }

        VaccinationRecord record = VaccinationRecord.register(command.idCard(), vaccine, command.idDoctor(),
                doseNumber, command.applicationDate(), command.batchNumber(), command.notes());

        return recordPort.save(record);
    }
}
