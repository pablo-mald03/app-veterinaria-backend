package com.happypets.app_veterinaria_backend.room.domain.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Principal room filter domain representation
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomFilter {

    private String name;
    private String location;
    private Integer number;
    private Boolean status;
}
