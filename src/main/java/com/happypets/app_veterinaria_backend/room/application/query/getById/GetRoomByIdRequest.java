package com.happypets.app_veterinaria_backend.room.application.query.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get room by id request class
 *
 */
@Data
@AllArgsConstructor
public class GetRoomByIdRequest implements Request<GetRoomByIdResponse>, AuditableRequest {
    private final Long roomId;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "HABITACIONES";
    }

    /**
     * Log action
     */
    @Override
    public String getAction() {
        return "BUSCAR HABITACION POR ID";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        GetRoomByIdResponse result = (GetRoomByIdResponse) response;
        return "Se consulto la habitacion con ID: '" + result.getId() + "' en el sistema";
    }
}