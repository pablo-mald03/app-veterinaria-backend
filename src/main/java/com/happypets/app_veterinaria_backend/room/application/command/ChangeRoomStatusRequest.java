package com.happypets.app_veterinaria_backend.room.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Principal change room status request class
 *
 */
@Getter
@AllArgsConstructor
public class ChangeRoomStatusRequest implements Request<ChangeRoomStatusResponse>, AuditableRequest {
    private final Long roomId;
    private final boolean status;

    /**
     * Log module
     */
    @Override
    public String getModule() {
        return "HABITACION";
    }

    /**
     * Log action
     */
    @Override
    public String getAction() {
        return this.status ? "ACTIVACION DE HABITACION" : "DESACTIVACION DE HABITACION";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        String actionText = this.status ? "Se activo la habitacion ID: " : "Se desactivo la habitacion ID: ";
        return actionText + this.roomId;
    }
}