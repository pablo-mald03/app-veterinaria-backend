package com.happypets.app_veterinaria_backend.sale.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.sale.application.service.SaleInventoryService;
import com.happypets.app_veterinaria_backend.sale.domain.entity.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Principal create sale handler
 *
 */
@Service
@RequiredArgsConstructor
public class CreateSaleHandler implements RequestHandler<CreateSaleRequest, CreateSaleResponse> {

    //Attributes
    private final SaleInventoryService saleInventoryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateSaleResponse handle(CreateSaleRequest request) {
        Sale savedSale = saleInventoryService.processSale(request);
        return new CreateSaleResponse(savedSale);
    }


    @Override
    public Class<CreateSaleRequest> getRequestType() {
        return CreateSaleRequest.class;
    }
}

