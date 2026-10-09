package com.happypets.app_veterinaria_backend.room.application.query.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Principal get room by id request class
 *
 */
@Data
@AllArgsConstructor
public class GetRoomByIdRequest implements Request<GetRoomByIdResponse> {
    private final Long roomId;
}