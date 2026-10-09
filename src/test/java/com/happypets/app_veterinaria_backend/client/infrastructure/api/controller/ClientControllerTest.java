package com.happypets.app_veterinaria_backend.client.infrastructure.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.happypets.app_veterinaria_backend.client.application.command.DeleteClientByIdCommand;
import com.happypets.app_veterinaria_backend.client.application.command.RegisterClientCommand;
import com.happypets.app_veterinaria_backend.client.application.command.UpdateClientCommand;
import com.happypets.app_veterinaria_backend.client.application.query.FindAllClientsQuery;
import com.happypets.app_veterinaria_backend.client.application.query.FindClientByIdQuery;
import com.happypets.app_veterinaria_backend.client.infrastructure.api.dto.ClientResponseDTO;
import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.infrastructure.config.AbstractControllerTest;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest extends AbstractControllerTest {

    // ============================================================
    // CUERPO JSON de prueba
    // ============================================================

    private static final String CUERPO_CLIENTE_VALIDO = """
            {
              "dpi": "1234567890123",
              "firstName": "Juan",
              "lastName": "Pérez",
              "email": "juan.perez@example.com",
              "phone": "+502 1234 5678",
              "address": "Calle 1, Zona 1"
            }
            """;

    private static final String CUERPO_CLIENTE_INCOMPLETO = """
            {
              "firstName": "Juan"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    // ============================================================
    // Helper: DTO de respuesta real
    // ============================================================

    private ClientResponseDTO crearResponseDto() {
        return new ClientResponseDTO(
                1L,
                "1234567890123",
                "Juan",
                "Pérez",
                "juan.perez@example.com",
                "+502 1234 5678",
                "Calle 1, Zona 1"
        );
    }

    // ============================================================
    // POST /clients  → 201
    // ============================================================

    @Test
    @DisplayName("POST /clients devuelve 201 con el cliente registrado")
    void debeRegistrarCliente() throws Exception {
        doReturn(crearResponseDto()).when(mediator).dispatch(any(RegisterClientCommand.class));

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_CLIENTE_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dpi").value("1234567890123"))
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@example.com"));

        verify(mediator).dispatch(any(RegisterClientCommand.class));
    }

    @Test
    @DisplayName("POST /clients con JSON malformado devuelve 400")
    void debeRechazarRegistroMalformado() throws Exception {
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ esto no es json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator);
    }

    @Test
    @DisplayName("POST /clients sin campos obligatorios devuelve 400")
    void debeRechazarRegistroSinCamposObligatorios() throws Exception {
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_CLIENTE_INCOMPLETO))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator);
    }

    @Test
    @DisplayName("POST /clients con DPI inválido devuelve 400")
    void debeRechazarDpiInvalido() throws Exception {
        String cuerpoDpiInvalido = """
                {
                  "dpi": "123",
                  "firstName": "Juan",
                  "lastName": "Pérez",
                  "email": "juan@example.com",
                  "phone": "+502 1234 5678"
                }
                """;

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpoDpiInvalido))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator);
    }

    // ============================================================
    // GET /clients/{id}
    // ============================================================

    @Test
    @DisplayName("GET /clients/{id} devuelve 200 con el cliente")
    void debeObtenerClientePorId() throws Exception {
        doReturn(crearResponseDto()).when(mediator).dispatch(any(FindClientByIdQuery.class));

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Pérez"));

        verify(mediator).dispatch(any(FindClientByIdQuery.class));
    }

    @Test
    @DisplayName("GET /clients/{id} con id no numérico devuelve 400")
    void debeRechazarIdClienteInvalido() throws Exception {
        mockMvc.perform(get("/clients/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator);
    }

    // ============================================================
    // GET /clients
    // ============================================================

    @Test
    @DisplayName("GET /clients devuelve 200 con la lista de clientes")
    void debeListarClientes() throws Exception {
        ClientResponseDTO cliente = crearResponseDto();
        doReturn(List.of(cliente)).when(mediator).dispatch(any(FindAllClientsQuery.class));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Juan"));

        verify(mediator).dispatch(any(FindAllClientsQuery.class));
    }

    @Test
    @DisplayName("GET /clients devuelve 200 con lista vacía")
    void debeListarClientesVacio() throws Exception {
        doReturn(List.of()).when(mediator).dispatch(any(FindAllClientsQuery.class));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(mediator).dispatch(any(FindAllClientsQuery.class));
    }

    // ============================================================
    // PUT /clients/{id}
    // ============================================================

    @Test
    @DisplayName("PUT /clients/{id} devuelve 200 con el cliente actualizado")
    void debeActualizarCliente() throws Exception {
        doReturn(crearResponseDto()).when(mediator).dispatch(any(UpdateClientCommand.class));

        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_CLIENTE_VALIDO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Juan"));

        verify(mediator).dispatch(any(UpdateClientCommand.class));
    }

    // ============================================================
    // DELETE /clients/{id}  → 204
    // ============================================================

    @Test
    @DisplayName("DELETE /clients/{id} devuelve 204 y despacha el comando")
    void debeEliminarCliente() throws Exception {
        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());

        verify(mediator).dispatch(any(DeleteClientByIdCommand.class));
    }

    @Test
    @DisplayName("DELETE /clients/{id} con id no numérico devuelve 400")
    void debeRechazarIdEliminarInvalido() throws Exception {
        mockMvc.perform(delete("/clients/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator);
    }
}