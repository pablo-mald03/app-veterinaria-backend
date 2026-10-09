package com.happypets.app_veterinaria_backend.vaccination.application.query.getall;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetAllVaccineResponse {

    private PaginationResult<Vaccine> vaccinePage;
}
