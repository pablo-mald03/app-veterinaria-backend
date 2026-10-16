package com.happypets.app_veterinaria_backend.product.application.command.patch;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal handler class to change status for product
 *
 */
@Service
@RequiredArgsConstructor
public class ChangeStatusProductHandler implements RequestHandler<ChangeStatusProductRequest, Void> {

    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void handle(ChangeStatusProductRequest request) {

        productRepository.changeStatus(request.getId(), request.getStatus());

        return null;
    }

    @Override
    public Class<ChangeStatusProductRequest> getRequestType() {
        return ChangeStatusProductRequest.class;
    }
}