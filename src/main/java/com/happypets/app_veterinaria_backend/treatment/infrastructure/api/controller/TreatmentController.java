package com.happypets.app_veterinaria_backend.treatment.infrastructure.api.controller;

import com.happypets.app_veterinaria_backend.treatment.application.command.CreateTreatmentHandler;
import com.happypets.app_veterinaria_backend.treatment.application.command.DeactivateTreatmentHandler;
import com.happypets.app_veterinaria_backend.treatment.application.query.TreatmentQueryHandler;
import com.happypets.app_veterinaria_backend.treatment.application.query.TreatmentView;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.infrastructure.api.dto.TreatmentRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/treatments")
@Tag(name = "Treatments", description = "Catálogo de tratamientos y los productos que consumen")
@RequiredArgsConstructor
public class TreatmentController {

    private final CreateTreatmentHandler createHandler;
    private final TreatmentQueryHandler queryHandler;
    private final DeactivateTreatmentHandler deactivateHandler;

    @PostMapping
    public ResponseEntity<TreatmentView> create(@Valid @RequestBody TreatmentRequestDTO request) {
        Treatment created = createHandler.execute(request.toCommand());
        return ResponseEntity
                .created(URI.create("/treatments/" + created.getIdTreatment()))
                .body(queryHandler.getById(created.getIdTreatment()));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentView>> getAll() {
        return ResponseEntity.ok(queryHandler.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentView> getById(@PathVariable Long id) {
        return ResponseEntity.ok(queryHandler.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateHandler.execute(id);
        return ResponseEntity.noContent().build();
    }
}
