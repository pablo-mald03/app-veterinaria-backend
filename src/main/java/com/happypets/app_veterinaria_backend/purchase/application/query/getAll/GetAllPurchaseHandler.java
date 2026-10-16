package com.happypets.app_veterinaria_backend.purchase.application.query.getAll;


import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.purchase.domain.entity.Purchase;
import com.happypets.app_veterinaria_backend.purchase.domain.filter.PurchaseFilter;
import com.happypets.app_veterinaria_backend.purchase.domain.port.PurchaseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get all purchase handler
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllPurchaseHandler implements RequestHandler<GetAllPurchaseRequest, GetAllPurchaseResponse> {

    private final PurchaseRepositoryPort purchaseRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetAllPurchaseResponse handle(GetAllPurchaseRequest request) {

        PurchaseFilter filter = PurchaseFilter.builder()
                .userId(request.getUserId())
                .status(request.getStatus())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .build();

        boolean loadDetails = Boolean.TRUE.equals(request.getWithDetails());

        PaginationResult<Purchase> domainResult = loadDetails
                ? purchaseRepositoryPort.findAllWithDetails(filter, request.getPaginationQuery())
                : purchaseRepositoryPort.findAll(filter, request.getPaginationQuery());

        return new GetAllPurchaseResponse(domainResult);
    }

    @Override
    public Class<GetAllPurchaseRequest> getRequestType() {
        return GetAllPurchaseRequest.class;
    }
}