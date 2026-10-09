package com.happypets.app_veterinaria_backend.room.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Principal change room status response class
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoomStatusResponse {
    private Long id;
    private boolean status;
}