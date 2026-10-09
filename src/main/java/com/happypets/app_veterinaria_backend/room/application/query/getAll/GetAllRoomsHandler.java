package com.happypets.app_veterinaria_backend.room.application.query.getAll;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.port.RoomRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal get all rooms handler class
 *
 */
@Service
@RequiredArgsConstructor
public class GetAllRoomsHandler implements RequestHandler<GetAllRoomsRequest, GetAllRoomsResponse> {

    private final RoomRepositoryPort roomRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public GetAllRoomsResponse handle(GetAllRoomsRequest request) {
        PaginationResult<Room> result = roomRepositoryPort.findAll(request.getFilter(), request.getPaginationQuery());
        return new GetAllRoomsResponse(result);
    }

    @Override
    public Class<GetAllRoomsRequest> getRequestType() {
        return GetAllRoomsRequest.class;
    }
}