package com.happypets.app_veterinaria_backend.client.application.query;

import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FindAllClientsQuery implements Request<List<ClientResponseDTO>> {

}
