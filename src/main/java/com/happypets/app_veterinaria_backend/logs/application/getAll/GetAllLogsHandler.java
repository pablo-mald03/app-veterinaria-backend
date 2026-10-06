package com.happypets.app_veterinaria_backend.logs.application.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.logs.domain.filter.LogFilter;
import com.happypets.app_veterinaria_backend.logs.domain.port.LogRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Get all logs handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllLogsHandler implements RequestHandler<GetAllLogsRequest, GetAllLogsResponse> {

    private final LogRepositoryPort logRepositoryPort;

    @Override
    public GetAllLogsResponse handle(GetAllLogsRequest request) {
        LogFilter filter = LogFilter.builder()
                .module(request.getModule())
                .createdFrom(request.getCreatedFrom())
                .createdTo(request.getCreatedTo())
                .build();

        return new GetAllLogsResponse(logRepositoryPort.findAll(filter, request.getPaginationQuery()));
    }

    @Override
    public Class<GetAllLogsRequest> getRequestType() {
        return GetAllLogsRequest.class;
    }
}
