package com.happypets.app_veterinaria_backend.logs.domain.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Principal log filter domain representation
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogFilter {
    private String module;
    private LocalDate createdFrom;
    private LocalDate createdTo;
}
