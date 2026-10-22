package com.happypets.app_veterinaria_backend.vaccinationcard.application.query;

import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.port.TreatmentRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.exception.VaccinationCardNotFoundException;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.TreatmentHistory;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.port.VaccinationRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPetTreatmentHistoryHandler {

    private final VaccinationCardRepositoryPort cardPort;
    private final VaccinationRecordRepositoryPort recordPort;
    private final TreatmentRepositoryPort treatmentPort;

    @Transactional(readOnly = true)
    public TreatmentHistory execute(Long idPet) {
        VaccinationCard card = cardPort.findByIdPet(idPet)
                .orElseThrow(() -> new VaccinationCardNotFoundException(idPet));

        LocalDate today = LocalDate.now();

        Map<Long, List<VaccinationRecord>> byTreatment = recordPort.findByIdCard(card.getIdCard()).stream()
                .collect(Collectors.groupingBy(
                        VaccinationRecord::getIdTreatment, LinkedHashMap::new, Collectors.toList()));

        List<TreatmentHistory.TreatmentLine> lines = byTreatment.entrySet().stream()
                .map(entry -> {
                    List<VaccinationRecord> history = entry.getValue().stream()
                            .sorted(Comparator.comparing(VaccinationRecord::getApplicationNumber))
                            .toList();

                    VaccinationRecord last = history.get(history.size() - 1);
                    LocalDate next = last.getNextDoseDate();

                    String name = treatmentPort.findById(entry.getKey())
                            .map(Treatment::getName)
                            .orElse("Tratamiento no encontrado (ID " + entry.getKey() + ")");

                    return new TreatmentHistory.TreatmentLine(
                            entry.getKey(),
                            name,
                            history.size(),
                            last.getApplicationDate(),
                            next,
                            next != null && next.isBefore(today),
                            history);
                })
                .toList();

        return new TreatmentHistory(card.getIdCard(), idPet, lines);
    }
}