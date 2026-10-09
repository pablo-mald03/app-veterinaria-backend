package com.happypets.app_veterinaria_backend.room.application.command.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Principal update room response class
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomResponse {
    private Long id;
    private String name;
    private String location;
    private String description;
    private int number;
    private boolean status;
}