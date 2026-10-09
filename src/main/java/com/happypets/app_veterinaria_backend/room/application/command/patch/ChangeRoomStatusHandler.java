package com.happypets.app_veterinaria_backend.room.application.command.patch;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNotFoundException;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomSameStatusChangeException;
import com.happypets.app_veterinaria_backend.room.domain.port.RoomRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Prioncipal change room status handler class
 *
 */
@Service
@RequiredArgsConstructor
public class ChangeRoomStatusHandler implements RequestHandler<ChangeRoomStatusRequest, ChangeRoomStatusResponse> {

    private final RoomRepositoryPort roomRepositoryPort;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChangeRoomStatusResponse handle(ChangeRoomStatusRequest request) {

        Room existing = roomRepositoryPort.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Sala no encontrada: " + request.getRoomId()));

        if (existing.isStatus() == request.isStatus()) {
            String messageInfo = request.isStatus() ? "activada" : "desactivada";
            throw new RoomSameStatusChangeException("La sala ya esta " + messageInfo);
        }

        existing.setStatus(request.isStatus());
        Room updated = roomRepositoryPort.update(existing);

        return new ChangeRoomStatusResponse(updated.getId(), updated.isStatus());
    }

    @Override
    public Class<ChangeRoomStatusRequest> getRequestType() {
        return ChangeRoomStatusRequest.class;
    }
}