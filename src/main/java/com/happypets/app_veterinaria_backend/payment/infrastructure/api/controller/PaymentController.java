package com.happypets.app_veterinaria_backend.payment.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.payment.application.command.AddPaymentItemCommand;
import com.happypets.app_veterinaria_backend.payment.application.command.OpenPaymentCommand;
import com.happypets.app_veterinaria_backend.payment.application.command.ProcessPaymentCommand;
import com.happypets.app_veterinaria_backend.payment.application.query.DownloadInvoiceQuery;
import com.happypets.app_veterinaria_backend.payment.application.query.FindPaymentByAppointmentIdQuery;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentItemRequestDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.PaymentResponseDTO;
import com.happypets.app_veterinaria_backend.payment.infrastructure.api.dto.ProcessPaymentRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(name = "Cobros y Facturación", description = "Gestión de cuentas y cargos de citas médicas")
public class PaymentController {

    private final Mediator mediator;

    @PreAuthorize("hasAuthority('pagos:registrar')")
    @Operation(summary = "Abrir cuenta de cobro", description = "Inicializa una cuenta en 0 para una cita.")
    @PostMapping("/appointment/{appointmentId}")
    public ResponseEntity<PaymentResponseDTO> openPayment(@PathVariable Long appointmentId) {
        var command = new OpenPaymentCommand(appointmentId);
        PaymentResponseDTO response = mediator.dispatch(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('pagos:registrar')")
    @Operation(summary = "Agregar cargo a la cuenta", description = "Añade vacunas, insumos o consultas al ticket de cobro.")
    @PostMapping("/appointment/{appointmentId}/items")
    public ResponseEntity<PaymentResponseDTO> addItem(
            @PathVariable Long appointmentId,
            @Valid @RequestBody PaymentItemRequestDTO request) {
        var command = new AddPaymentItemCommand(appointmentId, request);
        PaymentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('pagos:ver')")
    @Operation(summary = "Obtener cuenta por Cita", description = "Retorna el ticket completo y sus cargos asociados a una cita específica.")
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<PaymentResponseDTO> getByAppointmentId(@PathVariable Long appointmentId) {
        var query = new FindPaymentByAppointmentIdQuery(appointmentId);
        PaymentResponseDTO response = mediator.dispatch(query);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('pagos:registrar')")
    @Operation(summary = "Procesar pago de cuenta", description = "Marca la cuenta como pagada y registra el método de pago.")
    @PatchMapping("/{paymentId}/pay")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @PathVariable Long paymentId,
            @Valid @RequestBody ProcessPaymentRequestDTO requestDTO) {
        var command = new ProcessPaymentCommand(paymentId, requestDTO);
        PaymentResponseDTO response = mediator.dispatch(command);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('facturacion:ver')")
    @Operation(summary = "Descargar Factura PDF", description = "Genera y descarga el PDF de la factura pagada.")
    @GetMapping("/{paymentId}/invoice")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long paymentId) {
        var query = new DownloadInvoiceQuery(paymentId);
        byte[] pdfBytes = mediator.dispatch(query);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Esto le dice al navegador que descargue el archivo con este nombre
        headers.setContentDispositionFormData("attachment", "factura_" + paymentId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
