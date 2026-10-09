package com.happypets.app_veterinaria_backend.room.application.query.getById;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNotFoundException;
import com.happypets.app_veterinaria_backend.room.domain.port.RoomRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get room by id handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetRoomByIdHandler implements RequestHandler<GetRoomByIdRequest, GetRoomByIdResponse> {

    private final RoomRepositoryPort roomRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetRoomByIdResponse handle(GetRoomByIdRequest request) {

        Room room = roomRepositoryPort.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Sala no encontrada: " + request.getRoomId()));

        return new GetRoomByIdResponse(
                room.getId(),
                room.getName(),
                room.getLocation(),
                room.getDescription(),
                room.getNumber(),
                room.isStatus());
    }

    @Override
    public Class<GetRoomByIdRequest> getRequestType() {
        return GetRoomByIdRequest.class;
    }
}