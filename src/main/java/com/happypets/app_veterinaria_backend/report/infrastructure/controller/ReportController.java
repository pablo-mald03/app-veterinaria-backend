package com.happypets.app_veterinaria_backend.report.infrastructure.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.report.application.attendedpets.GetAttendedPetsRequest;
import com.happypets.app_veterinaria_backend.report.application.pendingvaccines.GetPendingVaccinesRequest;
import com.happypets.app_veterinaria_backend.report.application.treatments.GetFrequentTreatmentsRequest;
import com.happypets.app_veterinaria_backend.report.domain.model.AttendedPetRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingTreatmentRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.PendingVaccineRecord;
import com.happypets.app_veterinaria_backend.report.domain.model.TreatmentFrequency;
import com.happypets.app_veterinaria_backend.report.infrastructure.pdf.PdfReportGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;


@RestController
@RequestMapping("/reports")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Reports", description = "Module to manage the application reports")
@RequiredArgsConstructor
public class ReportController {

    private final Mediator mediator;
    private final PdfReportGenerator pdfGenerator;

    @GetMapping("/attended-pets")
    public ResponseEntity<?> attendedPets(
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "json") String format) {

        List<AttendedPetRecord> data = mediator.dispatch(new GetAttendedPetsRequest(from, to));
        return render(format, data, "mascotas-atendidas", () -> pdfGenerator.attendedPets(data));
    }

    @GetMapping("/frequent-treatments")
    public ResponseEntity<?> frequentTreatments(
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "json") String format) {

        List<TreatmentFrequency> data = mediator.dispatch(new GetFrequentTreatmentsRequest(from, to));
        return render(format, data, "tratamientos-frecuentes", () -> pdfGenerator.frequentTreatments(data));
    }

    @GetMapping("/pending-vaccines")
    public ResponseEntity<?> pendingVaccines(
            @RequestParam(defaultValue = "30") int days,
            @RequestParam(defaultValue = "json") String format) {

        LocalDate until = LocalDate.now().plusDays(days);
        List<PendingTreatmentRecord> data = mediator.dispatch(new GetPendingVaccinesRequest(until));
        return render(format, data, "vacunas-pendientes", () -> pdfGenerator.pendingTreatments(data));
    }

    private ResponseEntity<?> render(String format, Object jsonBody, String fileName, Supplier<byte[]> pdfSupplier) {
        if ("pdf".equalsIgnoreCase(format)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + ".pdf\"")
                    .body(pdfSupplier.get());
        }
        if ("json".equalsIgnoreCase(format)) {
            return ResponseEntity.ok(jsonBody);
        }
        return ResponseEntity.badRequest().body("format debe ser 'json' o 'pdf'");
    }
}
