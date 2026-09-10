package com.happypets.app_veterinaria_backend.pets.application.query.getbyid;

import com.happypets.app_veterinaria_backend.pets.domain.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/*
 * LAS ACCIONES QUE RETORNAN UN TIPO DE DATO
 *
 * VAN DENTRO DEL PAQUETE DE query
 *
 *
 * SIEMPRE SE RECOMIENDA SEGUIR EL ESTANDAR
 *
 * NameObjectActionResponse
 *
 * REPRESENTA A UN OBJETO QUE SE RETORNARA
 *
 * */
@Data
@Builder
@AllArgsConstructor
public class GetPetByIdResponse {

    private Pet pet;
}
