package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the principal create room dto
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequestDto {

    @NotBlank(message = "El nombre de la sala es obligatorio")
    @Size(max = 120, message = "El nombre de la sala debe tener maximo 120 caracteres")
    @Pattern(regexp = ".*[\\p{L}\\p{N}].*", message = "El nombre debe contener al menos una letra o numero")
    private String name;

    @Size(max = 150, message = "La ubicacion debe tener maximo 150 caracteres")
    private String location;

    @Size(max = 255, message = "La descripcion debe tener maximo 255 caracteres")
    private String description;

    @NotNull(message = "El numero de sala es obligatorio")
    @Positive(message = "El numero de sala debe ser mayor a 0")
    private Integer number;
}