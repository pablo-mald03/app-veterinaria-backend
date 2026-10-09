package com.happypets.app_veterinaria_backend.room.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Room domain entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private Long id;
    private String name;
    private String normalizedName;
    private String location;
    private String description;
    private int number;
    private boolean status;
}