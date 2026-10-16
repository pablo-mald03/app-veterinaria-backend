package com.happypets.app_veterinaria_backend.product.application.query.getById;


import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import com.happypets.app_veterinaria_backend.product.domain.exception.ProductNotFountException;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Principal recover get all categories handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, GetProductByIdResponse> {

    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetProductByIdResponse handle(GetProductByIdRequest request) {

        ProductDetail productDetail = productRepository.findById(request.getId())
                .orElseThrow(() -> new ProductNotFountException("Producto no encontrado con ID: " + request.getId()));

        return new GetProductByIdResponse(productDetail);
    }

    @Override
    public Class<GetProductByIdRequest> getRequestType() {
        return GetProductByIdRequest.class;
    }
}