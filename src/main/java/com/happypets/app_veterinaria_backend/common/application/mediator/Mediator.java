package com.happypets.app_veterinaria_backend.common.application.mediator;

import com.happypets.app_veterinaria_backend.auth.domain.port.AuthenticatedUserPort;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import com.happypets.app_veterinaria_backend.logs.domain.port.LogRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Principal mediator class to define the dependency inversion for the different ports
 *
 */
@Component
@Slf4j
public class Mediator {

    private final Map<? extends Class<?>, RequestHandler<?, ?>> requestHandlerMap;

    private final LogRepositoryPort logRepositoryPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    /**
     * Principal mediator constructor
     *
     */
    public Mediator(List<RequestHandler<?, ?>> requestHandlers, LogRepositoryPort logRepositoryPort, AuthenticatedUserPort authenticatedUserPort) {
        requestHandlerMap = requestHandlers.stream().collect(Collectors.toMap(RequestHandler::getRequestType, Function.identity()));
        this.logRepositoryPort = logRepositoryPort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    /**
     * Principal request dispatcher
     *
     */
    @SuppressWarnings("unchecked")
    public <R, T extends Request<R>> R dispatch(T request) {

        RequestHandler<T, R> handler = (RequestHandler<T, R>) requestHandlerMap.get(request.getClass());

        if (handler == null) {
            log.error("No handler found for request type: {}", request.getClass());
            throw new RuntimeException("No handler found for request type: " + request.getClass());
        }

        R response = handler.handle(request);

        if (request instanceof AuditableRequest auditable) {
            try {
                Long userId = authenticatedUserPort.getUserId();
                logRepositoryPort.registerLog(userId, auditable.getModule(), auditable.getAction(), auditable.getDetail(response));
            } catch (Exception e) {
                log.error("Error to log the operation {}: {}", request.getClass().getSimpleName(), e.getMessage(), e);
            }
        }

        return response;
    }

    /**
     * Async dispatcher
     *
     */
    @Async
    public <R, T extends Request<R>> void dispatchAsync(T request) {
        this.dispatch(request);
    }
}