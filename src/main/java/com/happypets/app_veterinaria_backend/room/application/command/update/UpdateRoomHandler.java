package com.happypets.app_veterinaria_backend.room.application.command.update;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.room.application.service.RoomNameNormalizerService;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNameAlreadyExistsException;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNotFoundException;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNumberAlreadyExistsException;
import com.happypets.app_veterinaria_backend.room.domain.port.RoomRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal update room handler class
 *
 */
@Service
@RequiredArgsConstructor
public class UpdateRoomHandler implements RequestHandler<UpdateRoomRequest, UpdateRoomResponse> {

    private final RoomRepositoryPort roomRepositoryPort;

    private final RoomNameNormalizerService roomNameNormalizerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateRoomResponse handle(UpdateRoomRequest request) {

        Room existing = roomRepositoryPort.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Sala no encontrada: " + request.getRoomId()));

        String name = roomNameNormalizerService.sanitize(request.getName());
        String normalizedName = roomNameNormalizerService.normalize(name);

        if (roomRepositoryPort.existsByNumberAndIdNot(request.getNumber(), existing.getId())) {
            throw new RoomNumberAlreadyExistsException(
                    "Ya existe otra sala con el numero " + request.getNumber());
        }

        if (roomRepositoryPort.existsByNormalizedNameAndIdNot(normalizedName, existing.getId())) {
            throw new RoomNameAlreadyExistsException(
                    "Ya existe otra sala con un nombre equivalente a: " + name);
        }

        existing.setName(name);
        existing.setNormalizedName(normalizedName);
        existing.setLocation(roomNameNormalizerService.cleanOptional(request.getLocation()));
        existing.setDescription(roomNameNormalizerService.cleanOptional(request.getDescription()));
        existing.setNumber(request.getNumber());

        Room updated = roomRepositoryPort.update(existing);

        return new UpdateRoomResponse(
                updated.getId(),
                updated.getName(),
                updated.getLocation(),
                updated.getDescription(),
                updated.getNumber(),
                updated.isStatus());
    }

    @Override
    public Class<UpdateRoomRequest> getRequestType() {
        return UpdateRoomRequest.class;
    }
}