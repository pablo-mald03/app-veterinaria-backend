package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.controller;

import com.happypets.app_veterinaria_backend.vaccinationrecord.application.command.RegisterVaccinationRecordHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.query.GetPendingVaccinationRecordsHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.query.GetVaccinationRecordsByCardHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.domain.model.VaccinationRecord;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordRequestDTO;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccination-records")
@Tag(name = "Treatment Applications", description = "Aplicación de tratamientos a mascotas")
@RequiredArgsConstructor
public class VaccinationRecordController {

    private final RegisterVaccinationRecordHandler registerHandler;
    private final GetVaccinationRecordsByCardHandler getByCardHandler;
    private final GetPendingVaccinationRecordsHandler pendingHandler;

    @PostMapping
    public ResponseEntity<VaccinationRecordResponseDTO> register(@Valid @RequestBody VaccinationRecordRequestDTO request) {
        VaccinationRecord record = registerHandler.execute(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(VaccinationRecordResponseDTO.from(record));
    }

    @GetMapping("/{idCard}")
    public ResponseEntity<List<VaccinationRecordResponseDTO>> getByCard(@PathVariable Long idCard) {
        return ResponseEntity.ok(getByCardHandler.execute(idCard).stream()
                .map(VaccinationRecordResponseDTO::from).toList());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<VaccinationRecordResponseDTO>> pending(@RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(pendingHandler.execute(days).stream()
                .map(VaccinationRecordResponseDTO::from).toList());
    }
}