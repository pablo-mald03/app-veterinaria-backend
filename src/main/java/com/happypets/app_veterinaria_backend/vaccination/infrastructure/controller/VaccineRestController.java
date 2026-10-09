package com.happypets.app_veterinaria_backend.vaccination.infrastructure.controller;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineRequestDTO;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface VaccineRestController {

    ResponseEntity<PaginationResult<VaccineResponseDTO>> getAllVaccine(int pageNumber, int pageSize, String sortBy, String direction);

    ResponseEntity<VaccineResponseDTO> getVaccineById(@PathVariable Long id);

    ResponseEntity<Void> saveVaccine( @RequestBody @Valid VaccineRequestDTO vaccine );

    ResponseEntity<Void> updateVaccine( @PathVariable Long id, @RequestBody @Valid VaccineRequestDTO vaccine );

    ResponseEntity<Void> deleteVaccine( @PathVariable Long id );
}
