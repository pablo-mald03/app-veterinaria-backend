package com.happypets.app_veterinaria_backend.room.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal create room request class
 *
 */
@Data
@AllArgsConstructor
public class CreateRoomRequest implements Request<CreateRoomResponse>, AuditableRequest {
    private final String name;
    private final String location;
    private final String description;
    private final Integer number;

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
        return "CREACION DE HABITACION";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        if (response instanceof CreateRoomResponse created) {
            return "Se creo la habitacion ID: " + created.getId() + " (numero " + created.getNumber() + ")";
        }
        return "Se creo la habitacion numero " + this.number;
    }
}