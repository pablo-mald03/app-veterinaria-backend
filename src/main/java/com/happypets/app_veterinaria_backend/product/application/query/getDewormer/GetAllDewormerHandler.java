package com.happypets.app_veterinaria_backend.product.application.query.getDewormer;

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
public class GetAllDewormerHandler implements RequestHandler<GetAllDewormerRequest, GetAllDewormerResponse> {

    //Attributes
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public GetAllDewormerResponse handle(GetAllDewormerRequest request) {
        ProductFilter filter = ProductFilter.builder()
                .priceFrom(request.getPriceFrom())
                .priceTo(request.getPriceTo())
                .sku(request.getSku())
                .categoryId(2L)
                .provider(request.getProvider())
                .build();

        PaginationResult<ProductDetail> products = productRepositoryPort.findAll(filter, request.getPaginationQuery());
        return new GetAllDewormerResponse(products);
    }


    @Override
    public Class<GetAllDewormerRequest> getRequestType() {
        return GetAllDewormerRequest.class;
    }
}