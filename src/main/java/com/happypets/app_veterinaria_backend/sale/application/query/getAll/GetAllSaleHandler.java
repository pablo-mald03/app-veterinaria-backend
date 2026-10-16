package com.happypets.app_veterinaria_backend.sale.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import com.happypets.app_veterinaria_backend.sale.domain.filter.SaleFilter;
import com.happypets.app_veterinaria_backend.sale.domain.port.SaleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get all sales handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllSaleHandler implements RequestHandler<GetAllSaleRequest, GetAllSaleResponse> {

    private final SaleRepositoryPort saleRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetAllSaleResponse handle(GetAllSaleRequest request) {

        SaleFilter filter = SaleFilter.builder()
                .billNumber(request.getBillNumber())
                .userId(request.getUserId())
                .status(request.getStatus())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build();

        boolean loadDetails = Boolean.TRUE.equals(request.getWithDetails());

        PaginationResult<Sale> domainResult = loadDetails
                ? saleRepositoryPort.findAllWithDetails(filter, request.getPaginationQuery())
                : saleRepositoryPort.findAll(filter, request.getPaginationQuery());

        return new GetAllSaleResponse(domainResult);
    }

    @Override
    public Class<GetAllSaleRequest> getRequestType() {
        return GetAllSaleRequest.class;
    }
}