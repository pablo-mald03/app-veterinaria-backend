package com.happypets.app_veterinaria_backend.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer summary domain model
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientSummary {
    private Long id;
    private String firstName;
    private String dpi;
}