package com.happypets.app_veterinaria_backend.vaccination.domain.port;

import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationQuery;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;

import java.util.Optional;

public interface VaccineRepository {

    Vaccine save(Vaccine vaccine);

    Optional<Vaccine> findById(Long id);

    PaginationResult<Vaccine> findAll(PaginationQuery paginationQuery);

    void desactiveById(Long id);
}
