package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.controller;

import com.happypets.app_veterinaria_backend.vaccinationcard.application.command.create.CreateVaccinationCardHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.application.query.getbyid.GetVaccinationCardByPetHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.application.query.report.GetVaccinationCardReportHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCardReport;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.pdf.VaccinationCardPdfGenerator;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.dto.VaccinationCardResponseDTO;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.mapper.VaccinationCardMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaccination-cards")
@Tag(name = "Vaccination Card", description = "Module to manage the application vaccination card")
@SecurityRequirement(name = "cookieAuth")
@RequiredArgsConstructor
public class VaccinationCardController {

    private final CreateVaccinationCardHandler createHandler;
    private final GetVaccinationCardByPetHandler getByPetHandler;
    private final VaccinationCardMapper mapper;
    private final GetVaccinationCardReportHandler reportHandler;
    private final VaccinationCardPdfGenerator pdfGenerator;

    @PreAuthorize("hasAuthority('vacunacion:crear')")
    @PostMapping("/pet/{idPet}")
    public ResponseEntity<VaccinationCardResponseDTO> create(@PathVariable Long idPet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(createHandler.execute(idPet)));
    }

    @PreAuthorize("hasAuthority('vacunacion:ver')")
    @GetMapping("/pet/{idPet}")
    public ResponseEntity<VaccinationCardResponseDTO> getByPet(@PathVariable Long idPet) {
        return ResponseEntity.ok(mapper.toResponse(getByPetHandler.execute(idPet)));
    }

    @PreAuthorize("hasAuthority('vacunacion:ver')")
    @GetMapping(value = "/pet/{idPet}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long idPet) {
        VaccinationCardReport report = reportHandler.execute(idPet);
        byte[] pdf = pdfGenerator.generate(report);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"carnet-vacunacion-mascota-" + idPet + ".pdf\"")
                .body(pdf);
    }
}
