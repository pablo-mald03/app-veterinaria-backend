package com.happypets.app_veterinaria_backend.client.application.command;

import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteClientByIdCommand implements Request<Boolean> {
    private Long id;
}
