package com.happypets.app_veterinaria_backend.room.infrastructure.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This is the principal change room status dto
 *
 */
@Data
@AllArgsConstructor
public class ChangeRoomStatusRequestDto {

    @NotNull(message = "El estado de la sala es obligatorio")
    private Boolean status;
}