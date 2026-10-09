package com.happypets.app_veterinaria_backend.appointment.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.appointment.application.command.*;
import com.happypets.app_veterinaria_backend.appointment.application.query.FindAllAppointmentsQuery;
import com.happypets.app_veterinaria_backend.appointment.application.query.FindAppointmentByIdQuery;
import com.happypets.app_veterinaria_backend.appointment.application.query.FindMedicalHistoryByPetIdQuery;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentRequestDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentResponseDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.AppointmentStatusRequestDTO;
import com.happypets.app_veterinaria_backend.appointment.infrastructure.api.dto.MedicalConsultationRequestDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@Tag(name = "Gestión de Citas", description = "Endpoints para agendar y manejar consultas médicas")
public class AppointmentController {

    private final Mediator mediator;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> register(@RequestBody @Valid AppointmentRequestDTO requestDTO) {
        var command = new RegisterAppointmentCommand(requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAll() {
        var query = new FindAllAppointmentsQuery();
        List<AppointmentResponseDTO> response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) {
        var query = new FindAppointmentByIdQuery(id);
        AppointmentResponseDTO response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentRequestDTO requestDTO) {
        var command = new UpdateAppointmentCommand(id, requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var command = new DeleteAppointmentByIdCommand(id);
        mediator.dispatch(command);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/diagnosis")
    public ResponseEntity<AppointmentResponseDTO> updateDiagnosis(
            @PathVariable Long id,
            @RequestBody @Valid MedicalConsultationRequestDTO requestDTO) {
        var command = new UpdateAppointmentDiagnosisCommand(id, requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponseDTO> changeStatus(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentStatusRequestDTO requestDTO) {
        var command = new ChangeAppointmentStatusCommand(id, requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener el historial médico de una mascota")
    @GetMapping("/pet/{petId}/history")
    public ResponseEntity<List<AppointmentResponseDTO>> getMedicalHistory(@PathVariable Long petId) {
        var query = new FindMedicalHistoryByPetIdQuery(petId);
        List<AppointmentResponseDTO> history = mediator.dispatch(query);

        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(history);
    }
}
