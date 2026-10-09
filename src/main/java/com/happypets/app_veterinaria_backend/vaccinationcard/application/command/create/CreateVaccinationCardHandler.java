package com.happypets.app_veterinaria_backend.vaccinationcard.application.command.create;

import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCard;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.port.VaccinationCardRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateVaccinationCardHandler {

    private final VaccinationCardRepositoryPort cardPort;

    /*CADA MASCOTA SOLO TENDRA UN CARNET SI YA EXISTIERA LO DEVUELVE*/
    @Transactional
    public VaccinationCard execute(Long idPet) {
        return cardPort.findByIdPet(idPet).orElseGet(() -> cardPort.save(VaccinationCard.builder()
                .idPet(idPet).creationDate(LocalDate.now()).status(true).build()));
    }
}
