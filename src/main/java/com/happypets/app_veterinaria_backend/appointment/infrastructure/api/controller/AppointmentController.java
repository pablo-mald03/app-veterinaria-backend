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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@Tag(name = "Gestión de Citas", description = "Endpoints para agendar y manejar consultas médicas")
public class AppointmentController {

    private final Mediator mediator;

    @PreAuthorize("hasAuthority('citas:crear')")
    @Operation(
            summary = "Agendar una nueva cita",
            description = "Crea una nueva cita médica validando la disponibilidad de horario del veterinario y la sala."
    )
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> register(@RequestBody @Valid AppointmentRequestDTO requestDTO) {
        var command = new RegisterAppointmentCommand(requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('citas:ver')")
    @Operation(
            summary = "Listar todas las citas",
            description = "Obtiene un listado completo de todas las citas registradas en el sistema, ordenadas por fecha."
    )
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAll() {
        var query = new FindAllAppointmentsQuery();
        List<AppointmentResponseDTO> response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('citas:ver')")
    @Operation(
            summary = "Obtener detalle de cita",
            description = "Busca y retorna los detalles completos de una cita específica utilizando su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id) {
        var query = new FindAppointmentByIdQuery(id);
        AppointmentResponseDTO response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('citas:editar')")
    @Operation(
            summary = "Actualizar una cita existente",
            description = "Modifica los datos generales de una cita agendada (fecha, hora, veterinario, sala o motivo)."
    )
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentRequestDTO requestDTO) {
        var command = new UpdateAppointmentCommand(id, requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('citas:eliminar')")
    @Operation(
            summary = "Eliminar una cita",
            description = "Realiza la eliminación de una cita específica en el sistema."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var command = new DeleteAppointmentByIdCommand(id);
        mediator.dispatch(command);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('citas:diagnosticar')")
    @Operation(
            summary = "Registrar diagnóstico de consulta",
            description = "Añade o actualiza el diagnóstico, tratamiento y costo al finalizar una consulta médica."
    )
    @PatchMapping("/{id}/diagnosis")
    public ResponseEntity<AppointmentResponseDTO> updateDiagnosis(
            @PathVariable Long id,
            @RequestBody @Valid MedicalConsultationRequestDTO requestDTO) {
        var command = new UpdateAppointmentDiagnosisCommand(id, requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyAuthority('citas:editar', 'citas:cancelar')")
    @Operation(
            summary = "Cambiar estado de cita",
            description = "Actualiza el estado actual de la cita (ej. SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED)."
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponseDTO> changeStatus(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentStatusRequestDTO requestDTO) {
        var command = new ChangeAppointmentStatusCommand(id, requestDTO);
        AppointmentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('citas:ver')")
    @Operation(
            summary = "Obtener el historial médico de una mascota",
            description = "Retorna el registro completo de citas previas asociadas al ID de una mascota específica."
    )
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
