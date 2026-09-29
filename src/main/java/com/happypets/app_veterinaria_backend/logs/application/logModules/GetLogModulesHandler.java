package com.happypets.app_veterinaria_backend.logs.application.logModules;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.logs.domain.port.LogRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Get log modules handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetLogModulesHandler implements RequestHandler<GetLogModulesRequest, GetLogModulesResponse> {
    private final LogRepositoryPort logRepositoryPort;

    @Override
    public GetLogModulesResponse handle(GetLogModulesRequest request) {
        return new GetLogModulesResponse(logRepositoryPort.findDistinctModules());
    }

    @Override
    public Class<GetLogModulesRequest> getRequestType() {
        return GetLogModulesRequest.class;
    }
}
