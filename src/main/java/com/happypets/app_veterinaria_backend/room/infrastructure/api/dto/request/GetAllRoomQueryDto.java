package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Query params to filter the rooms
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllRoomQueryDto {

    @Size(max = 120, message = "El nombre debe tener maximo 120 caracteres")
    private String name;

    @Size(max = 150, message = "La ubicacion debe tener maximo 150 caracteres")
    private String location;

    @Positive(message = "El numero de sala debe ser mayor a 0")
    private Integer number;

    private Boolean status;
}