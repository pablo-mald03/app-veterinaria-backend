package com.happypets.app_veterinaria_backend.room.application.query.getById;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Principal get room by id response class
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomByIdResponse {
    private Long id;
    private String name;
    private String location;
    private String description;
    private int number;
    private boolean status;
}