package com.happypets.app_veterinaria_backend.payment.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.payment.application.command.AddPaymentItemCommand;
import com.happypets.app_veterinaria_backend.payment.application.command.OpenPaymentCommand;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentItemRequestDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "Cobros y Facturación", description = "Gestión de cuentas y cargos de citas médicas")
public class PaymentController {

    private final Mediator mediator;

    @Operation(summary = "Abrir cuenta de cobro", description = "Inicializa una cuenta en 0 para una cita.")
    @PostMapping("/appointment/{appointmentId}")
    public ResponseEntity<PaymentResponseDTO> openPayment(@PathVariable Long appointmentId) {
        var command = new OpenPaymentCommand(appointmentId);
        PaymentResponseDTO response = mediator.dispatch(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Agregar cargo a la cuenta", description = "Añade vacunas, insumos o consultas al ticket de cobro.")
    @PostMapping("/appointment/{appointmentId}/items")
    public ResponseEntity<PaymentResponseDTO> addItem(
            @PathVariable Long appointmentId,
            @Valid @RequestBody PaymentItemRequestDTO request) {
        var command = new AddPaymentItemCommand(appointmentId, request);
        PaymentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }
}
