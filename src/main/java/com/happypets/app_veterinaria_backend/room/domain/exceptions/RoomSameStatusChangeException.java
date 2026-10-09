package com.happypets.app_veterinaria_backend.room.domain.exceptions;

import com.happypets.app_veterinaria_backend.common.domain.exception.BusinessRuleException;

/**
 * Principal exception to notify when the room has the same status
 *
 */
public class RoomSameStatusChangeException extends BusinessRuleException {
    public RoomSameStatusChangeException(String message) {
        super(message);
    }
}
