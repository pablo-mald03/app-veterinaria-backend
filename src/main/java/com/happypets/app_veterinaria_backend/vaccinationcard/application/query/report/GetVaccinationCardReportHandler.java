package com.happypets.app_veterinaria_backend.vaccinationcard.application.query.report;

import com.happypets.app_veterinaria_backend.pets.domain.exception.PetNotFoundException;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.domain.port.PetRepository;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
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
    private final TreatmentRepositoryPort treatmentPort;
    private final PetRepository petRepository;

    public VaccinationCardReport execute(Long idPet) {
        VaccinationCard card = cardPort.findByIdPet(idPet)
                .orElseThrow(() -> new VaccinationCardNotFoundException(idPet));

        Pet pet = petRepository.findById(idPet)
                .orElseThrow(() -> new PetNotFoundException(idPet));

        List<VaccinationRecord> records = recordPort.findByIdCard(card.getIdCard());

        Map<Long, String> treatmentNames = new HashMap<>();
        List<VaccinationCardReport.Entry> entries = records.stream()
                .map(record -> new VaccinationCardReport.Entry(
                        treatmentNames.computeIfAbsent(record.getIdTreatment(), this::findTreatmentName),
                        record.getApplicationNumber(),
                        record.getApplicationDate(),
                        record.getNextDoseDate(),
                        record.getTotalCost(),
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

    private String findTreatmentName(Long idTreatment) {
        return treatmentPort.findById(idTreatment)
                .map(Treatment::getName)
                .orElse("Tratamiento no encontrado (ID " + idTreatment + ")");
    }
}