package com.happypets.app_veterinaria_backend.product.application.query.getExpiringProducts;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.product.domain.entity.ExpiringProductSummary;
import com.happypets.app_veterinaria_backend.product.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Principal handler class for expiring products
 *
 */
@Service
@RequiredArgsConstructor
public class GetExpiringProductsRequestHandler implements RequestHandler<GetExpiringProductsRequest, GetExpiringProductsResponse> {

    private final ProductRepositoryPort productRepositoryPort;
    private final Clock clock;

    @Override
    @Transactional(readOnly = true)
    public GetExpiringProductsResponse handle(GetExpiringProductsRequest request) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime limit = now.plusDays(request.getDays());

        PaginationResult<ExpiringProductSummary> result = productRepositoryPort.findExpiringProducts(now, limit, request.getPaginationQuery());

        return new GetExpiringProductsResponse(result);
    }

    @Override
    public Class<GetExpiringProductsRequest> getRequestType() {
        return GetExpiringProductsRequest.class;
    }
}