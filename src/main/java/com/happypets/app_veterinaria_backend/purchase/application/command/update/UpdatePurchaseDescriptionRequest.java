package com.happypets.app_veterinaria_backend.purchase.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Principal udpate purchase description request
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePurchaseDescriptionRequest implements Request<Void>, AuditableRequest {

    private Long id;
    private String description;

    /**
     * Audit log module
     */
    @Override
    public String getModule() {
        return "COMPRAS";
    }

    /**
     * Audit log action
     */
    @Override
    public String getAction() {
        return "ACTUALIZACION DE DESCRIPCION DE COMPRA";
    }

    /**
     * Audit log detail
     */
    @Override
    public String getDetail(Object response) {
        return "Se actualizó la descripción de la compra ID " + this.id + " a: '" + this.description + "'";
    }
}