package com.happypets.app_veterinaria_backend.vaccination.application.command.create;

import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateVaccineResponse {

    private Vaccine vaccine;
}
