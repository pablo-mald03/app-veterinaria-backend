package com.happypets.app_veterinaria_backend.logs.application.logModules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Get log modules response class
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLogModulesResponse {
    private List<String> modules;
}