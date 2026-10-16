package com.happypets.app_veterinaria_backend.product.application.query.getAllVaccination;

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
public class GetAllVaccinateHandler implements RequestHandler<GetAllVaccinateRequest, GetAllVaccinateResponse> {

    //Attributes
    private final ProductRepositoryPort productRepositoryPort;

    @Override
    public GetAllVaccinateResponse handle(GetAllVaccinateRequest request) {
        ProductFilter filter = ProductFilter.builder()
                .priceFrom(request.getPriceFrom())
                .priceTo(request.getPriceTo())
                .sku(request.getSku())
                .categoryId(1L)
                .provider(request.getProvider())
                .build();

        PaginationResult<ProductDetail> products = productRepositoryPort.findAll(filter, request.getPaginationQuery());
        return new GetAllVaccinateResponse(products);
    }


    @Override
    public Class<GetAllVaccinateRequest> getRequestType() {
        return GetAllVaccinateRequest.class;
    }
}