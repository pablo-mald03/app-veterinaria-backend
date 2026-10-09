package com.happypets.app_veterinaria_backend.room.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import com.happypets.app_veterinaria_backend.common.domain.auditable.AuditableRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal update room request class
 *
 */
@Data
@AllArgsConstructor
public class UpdateRoomRequest implements Request<UpdateRoomResponse>, AuditableRequest {
    private final Long roomId;
    private final String name;
    private final String location;
    private final String description;
    private final Integer number;

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
        return "ACTUALIZACION DE HABITACION";
    }

    /**
     * Log detail
     */
    @Override
    public String getDetail(Object response) {
        return "Se actualizo la informacion de la habitacion ID: " + this.roomId;
    }
}
