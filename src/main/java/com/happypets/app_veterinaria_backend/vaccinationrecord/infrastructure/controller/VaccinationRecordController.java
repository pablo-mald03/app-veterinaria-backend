package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.controller;

import com.happypets.app_veterinaria_backend.vaccinationrecord.application.command.RegisterVaccinationRecordHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.query.GetPendingVaccinationRecordsHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.query.GetVaccinationRecordsByCardHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordRequestDTO;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordResponseDTO;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.mapper.VaccinationRecordMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccination-records")
@RequiredArgsConstructor
public class VaccinationRecordController {

    private final RegisterVaccinationRecordHandler registerHandler;
    private final GetVaccinationRecordsByCardHandler getByCardHandler;
    private final GetPendingVaccinationRecordsHandler pendingHandler;
    private final VaccinationRecordMapper mapper;

    @PostMapping
    public ResponseEntity<VaccinationRecordResponseDTO> register(@Valid @RequestBody VaccinationRecordRequestDTO request) {
        var record = registerHandler.execute(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(record));
    }

    @GetMapping("/{idCard}")
    public ResponseEntity<List<VaccinationRecordResponseDTO>> getByCard(@PathVariable Long idCard) {
        return ResponseEntity.ok(mapper.toResponseList(getByCardHandler.execute(idCard)));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<VaccinationRecordResponseDTO>> pending(@RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(mapper.toResponseList(pendingHandler.execute(days)));
    }
}
