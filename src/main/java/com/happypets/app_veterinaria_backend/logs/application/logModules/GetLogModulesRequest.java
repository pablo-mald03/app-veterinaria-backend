package com.happypets.app_veterinaria_backend.logs.application.logModules;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Get log modules request class
 *
 */
@Data
@AllArgsConstructor
public class GetLogModulesRequest implements Request<GetLogModulesResponse> {
}
