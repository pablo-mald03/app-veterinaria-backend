package com.happypets.app_veterinaria_backend.vaccination.infrastructure.controller;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.command.create.CreatePetResponse;
import com.happypets.app_veterinaria_backend.pets.application.command.delete.DeletePetRequest;
import com.happypets.app_veterinaria_backend.pets.application.query.getall.GetAllPetRequest;
import com.happypets.app_veterinaria_backend.pets.application.query.getall.GetAllPetResponse;
import com.happypets.app_veterinaria_backend.pets.application.query.getbyid.GetPetByIdRequest;
import com.happypets.app_veterinaria_backend.pets.application.query.getbyid.GetPetByIdResponse;
import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import com.happypets.app_veterinaria_backend.pets.infrastructure.dto.PetResponseDTO;
import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineResponse;
import com.happypets.app_veterinaria_backend.vaccination.application.command.delete.DeleteVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getall.GetAllVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getall.GetAllVaccineResponse;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getbyid.GetVaccineByIdRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getbyid.GetVaccineByIdResponse;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineRequestDTO;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineResponseDTO;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.mapper.VaccineMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/vaccines")
@Tag(name = "Vaccines", description = "Endpoints to manage vaccines")
@SecurityRequirement(name = "cookieAuth")
@Slf4j
public class VaccineController implements VaccineRestController{

    private final Mediator mediator;
    private final VaccineMapper vaccineMapper;

    @Override
    @GetMapping
    public ResponseEntity<PaginationResult<VaccineResponseDTO>> getAllVaccine(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        log.info("Getting all vaccines");

        PaginationQuery paginationQuery = new PaginationQuery(pageNumber, pageSize, sortBy, direction);

        GetAllVaccineResponse response = mediator.dispatch(new GetAllVaccineRequest(paginationQuery));

        PaginationResult<Vaccine> vaccines = response.getVaccinePage();

        PaginationResult<VaccineResponseDTO> vaccineDtoPage = new PaginationResult<>(
                vaccines.getContent().stream().map(vaccineMapper::toResponseDTO).toList(),
                vaccines.getPage(),
                vaccines.getSize(),
                vaccines.getTotalPages(),
                vaccines.getTotalElements()
        );

        return ResponseEntity.ok(vaccineDtoPage);
    }

    @Operation(summary = "Get product by id", description = "Get product by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<VaccineResponseDTO> getVaccineById(@PathVariable Long id) {
        log.info("Getting vaccine with id: {}", id);
        GetVaccineByIdResponse response = mediator.dispatch(new GetVaccineByIdRequest(id));
        VaccineResponseDTO vaccine = vaccineMapper.toResponseDTO(response.getVaccine());
        log.info("Found vaccine with id: {}", vaccine.getIdVaccine());
        return ResponseEntity.ok(vaccine);
    }

    @Operation(summary = "Save vaccine", description = "Save vaccine create")
    @PostMapping
    @Override
    public ResponseEntity<Void> saveVaccine(@RequestBody @Valid VaccineRequestDTO vaccine) {

        CreateVaccineRequest request = vaccineMapper.toCreateRequest(vaccine);
        CreateVaccineResponse response = mediator.dispatch(request);

        Vaccine saveVaccine = response.getVaccine();
        log.info("Vaccine with id {} was saved", saveVaccine.getIdVaccine());

        return ResponseEntity.created(
                URI.create("/vaccines/".concat(saveVaccine.getIdVaccine().toString()))
        ).build();
    }

    @Override
    public ResponseEntity<Void> updateVaccine(@PathVariable Long id, @RequestBody @Valid VaccineRequestDTO vaccine) {
        return null;
    }

    @Operation(summary = "Desactivate vaccine", description = "Desactivate a vaccine by id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) {
        log.info("Deactivating vaccine with id {}", id);
        mediator.dispatchAsync(new DeleteVaccineRequest(id));
        log.info("Vaccine with id {} accepted for deactivation", id);
        return ResponseEntity.accepted().build();
    }
}
