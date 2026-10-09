package com.happypets.app_veterinaria_backend.room.application.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal create room response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomResponse {
    private Long id;
    private String name;
    private String location;
    private String description;
    private int number;
    private boolean status;
}