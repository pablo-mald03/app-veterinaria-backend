package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.controller;

import com.happypets.app_veterinaria_backend.vaccinationcard.application.command.create.CreateVaccinationCardHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.application.query.getbyid.GetVaccinationCardByPetHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.dto.VaccinationCardResponseDTO;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.mapper.VaccinationCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccination-cards")
@RequiredArgsConstructor
public class VaccinationCardController {

    private final CreateVaccinationCardHandler createHandler;
    private final GetVaccinationCardByPetHandler getByPetHandler;
    private final VaccinationCardMapper mapper;

    @PostMapping("/pet/{idPet}")
    public ResponseEntity<VaccinationCardResponseDTO> create(@PathVariable Long idPet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(createHandler.execute(idPet)));
    }

    @GetMapping("/pet/{idPet}")
    public ResponseEntity<VaccinationCardResponseDTO> getByPet(@PathVariable Long idPet) {
        return ResponseEntity.ok(mapper.toResponse(getByPetHandler.execute(idPet)));
    }
}
