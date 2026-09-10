package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;

public record DeleteClientByIdCommand(Long id) implements Request<Boolean> {
}
