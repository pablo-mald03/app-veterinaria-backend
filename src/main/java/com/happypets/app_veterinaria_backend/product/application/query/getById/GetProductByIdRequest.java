package com.happypets.app_veterinaria_backend.product.application.query.getById;


import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get product by Id request
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductByIdRequest implements Request<GetProductByIdResponse>, AuditableRequest {
    private Long id;

    @Override
    public String getModule() {
        return "PRODUCTOS";
    }

    @Override
    public String getAction() {
        return "CONSULTAR PRODUCTOS POR ID";
    }

    @Override
    public String getDetail(Object response) {
        GetProductByIdResponse responseDTO = (GetProductByIdResponse) response;
        return "Se obtuvo el producto con ID: '" + responseDTO.getProductDetail().getProduct().getId() + "'";
    }
}