package com.happypets.app_veterinaria_backend.vaccinationcard.application.query.report;

import com.happypets.app_veterinaria_backend.pets.domain.exception.PetNotFoundException;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.domain.port.VaccineRepository;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.exception.VaccinationCardNotFoundException;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCardReport;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetVaccinationCardReportHandler {

    private final VaccinationCardRepositoryPort cardPort;
    private final VaccinationRecordRepositoryPort recordPort;
    private final VaccineRepository vaccineRepository;
    private final PetRepository petRepository;

    public VaccinationCardReport execute(Long idPet) {
        VaccinationCard card = cardPort.findByIdPet(idPet)
                .orElseThrow(() -> new VaccinationCardNotFoundException(idPet));

        Pet pet = petRepository.findById(idPet)
                .orElseThrow(() -> new PetNotFoundException(idPet));

        List<VaccinationRecord> records = recordPort.findByIdCard(card.getIdCard());

        Map<Long, String> vaccineNames = new HashMap<>();
        List<VaccinationCardReport.Entry> entries = records.stream()
                .map(record -> new VaccinationCardReport.Entry(
                        vaccineNames.computeIfAbsent(record.getIdVaccine(), this::findVaccineName),
                        record.getDoseNumber(),
                        record.getApplicationDate(),
                        record.getNextDoseDate(),
                        record.getBatchNumber(),
                        record.getNotes()))
                .toList();

        return new VaccinationCardReport(
                card.getIdCard(),
                card.getCreationDate(),
                pet.getIdPet(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getAge(),
                pet.getWeight(),
                entries);
    }

    private String findVaccineName(Long idVaccine) {
        return vaccineRepository.findById(idVaccine)
                .map(Vaccine::getName)
                .orElse("Vacuna no encontrada (ID " + idVaccine + ")");
    }
}
