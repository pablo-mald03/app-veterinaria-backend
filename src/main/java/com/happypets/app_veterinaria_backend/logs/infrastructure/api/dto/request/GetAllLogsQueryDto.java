package com.happypets.app_veterinaria_backend.logs.infrastructure.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Principal get all logs query dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllLogsQueryDto {
    private String module;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdTo;
}