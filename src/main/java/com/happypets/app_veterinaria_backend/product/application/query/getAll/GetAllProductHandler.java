package com.happypets.app_veterinaria_backend.product.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.ProductDetail;
import com.happypets.app_veterinaria_backend.product.domain.filter.ProductFilter;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Principal recover get all categories handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllProductHandler implements RequestHandler<GetAllProductRequest, GetAllProductResponse> {

    //Attributes
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public GetAllProductResponse handle(GetAllProductRequest request) {
        ProductFilter filter = ProductFilter.builder()
                .priceFrom(request.getPriceFrom())
                .priceTo(request.getPriceTo())
                .sku(request.getSku())
                .categoryId(request.getCategoryId())
                .provider(request.getProvider())
                .build();

        PaginationResult<ProductDetail> products = productRepositoryPort.findAll(filter, request.getPaginationQuery());
        return new GetAllProductResponse(products);
    }


    @Override
    public Class<GetAllProductRequest> getRequestType() {
        return GetAllProductRequest.class;
    }
}