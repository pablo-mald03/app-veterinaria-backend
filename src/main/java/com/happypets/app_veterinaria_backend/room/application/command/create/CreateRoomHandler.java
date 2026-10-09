package com.happypets.app_veterinaria_backend.room.application.command.create;

import com.happypets.app_veterinaria_backend.common.application.mediator.RequestHandler;
import com.happypets.app_veterinaria_backend.room.application.service.RoomNameNormalizerService;
import com.happypets.app_veterinaria_backend.room.domain.entity.Room;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNameAlreadyExistsException;
import com.happypets.app_veterinaria_backend.room.domain.exceptions.RoomNumberAlreadyExistsException;
import com.happypets.app_veterinaria_backend.room.domain.port.RoomRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Principal create room handler class
 *
 */
@Service
@RequiredArgsConstructor
public class CreateRoomHandler implements RequestHandler<CreateRoomRequest, CreateRoomResponse> {

    private final RoomRepositoryPort roomRepositoryPort;

    private final RoomNameNormalizerService roomNameNormalizerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateRoomResponse handle(CreateRoomRequest request) {

        String name = roomNameNormalizerService.sanitize(request.getName());
        String normalizedName = roomNameNormalizerService.normalize(name);

        if (roomRepositoryPort.existsByNumber(request.getNumber())) {
            throw new RoomNumberAlreadyExistsException(
                    "Ya existe una sala con el numero " + request.getNumber());
        }

        if (roomRepositoryPort.existsByNormalizedName(normalizedName)) {
            throw new RoomNameAlreadyExistsException(
                    "Ya existe una sala con un nombre equivalente a: " + name);
        }

        Room room = Room.builder()
                .name(name)
                .normalizedName(normalizedName)
                .location(roomNameNormalizerService.cleanOptional(request.getLocation()))
                .description(roomNameNormalizerService.cleanOptional(request.getDescription()))
                .number(request.getNumber())
                .status(true)
                .build();

        Room created = roomRepositoryPort.create(room);

        return new CreateRoomResponse(
                created.getId(),
                created.getName(),
                created.getLocation(),
                created.getDescription(),
                created.getNumber(),
                created.isStatus());
    }

    @Override
    public Class<CreateRoomRequest> getRequestType() {
        return CreateRoomRequest.class;
    }
}