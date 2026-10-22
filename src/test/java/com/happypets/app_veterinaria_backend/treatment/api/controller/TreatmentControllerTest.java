package com.happypets.app_veterinaria_backend.treatment.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happypets.app_veterinaria_backend.treatment.application.command.CreateTreatmentHandler;
import com.happypets.app_veterinaria_backend.treatment.application.command.DeactivateTreatmentHandler;
import com.happypets.app_veterinaria_backend.treatment.application.query.TreatmentQueryHandler;
import com.happypets.app_veterinaria_backend.treatment.application.query.TreatmentView;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.Treatment;
import com.happypets.app_veterinaria_backend.treatment.domain.entity.TreatmentProduct;
import com.happypets.app_veterinaria_backend.treatment.infrastructure.api.controller.TreatmentController;
import com.happypets.app_veterinaria_backend.treatment.infrastructure.api.dto.TreatmentRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TreatmentControllerTest {

    @Mock
    private CreateTreatmentHandler createHandler;

    @Mock
    private TreatmentQueryHandler queryHandler;

    @Mock
    private DeactivateTreatmentHandler deactivateHandler;

    @InjectMocks
    private TreatmentController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    // ============================================================
    // POST /treatments
    // ============================================================

    @Nested
    @DisplayName("POST /treatments")
    class CreateTreatment {

        @Test
        @DisplayName("Debe crear un tratamiento y devolver 201 con el recurso")
        void shouldCreateTreatment() throws Exception {
            TreatmentRequestDTO request = buildValidRequest();

            Treatment created = Treatment.builder()
                    .idTreatment(42L)
                    .name("Desparasitación canina")
                    .build();

            TreatmentView view = buildView(42L, "Desparasitación canina");

            when(createHandler.execute(any())).thenReturn(created);
            when(queryHandler.getById(42L)).thenReturn(view);

            mockMvc.perform(post("/treatments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "/treatments/42"))
                    .andExpect(jsonPath("$.idTreatment").value(42))
                    .andExpect(jsonPath("$.name").value("Desparasitación canina"));

            verify(createHandler, times(1)).execute(any());
            verify(queryHandler, times(1)).getById(42L);
        }

        @Test
        @DisplayName("Debe devolver 500 si el handler lanza excepción inesperada")
        void shouldReturn500WhenHandlerThrows() throws Exception {
            TreatmentRequestDTO request = buildValidRequest();

            when(createHandler.execute(any()))
                    .thenThrow(new RuntimeException("boom"));

            mockMvc.perform(post("/treatments")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isInternalServerError());

            verify(createHandler, times(1)).execute(any());
            verifyNoInteractions(queryHandler);
        }
    }

    // ============================================================
    // GET /treatments
    // ============================================================

    @Nested
    @DisplayName("GET /treatments")
    class GetAllTreatments {

        @Test
        @DisplayName("Debe devolver 200 con la lista de tratamientos")
        void shouldReturnAllTreatments() throws Exception {
            when(queryHandler.getAll()).thenReturn(List.of(
                    buildView(1L, "Desparasitación"),
                    buildView(2L, "Otitis")
            ));

            mockMvc.perform(get("/treatments"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].idTreatment").value(1))
                    .andExpect(jsonPath("$[1].idTreatment").value(2));

            verify(queryHandler, times(1)).getAll();
        }

        @Test
        @DisplayName("Debe devolver 200 con lista vacía si no hay tratamientos")
        void shouldReturnEmptyList() throws Exception {
            when(queryHandler.getAll()).thenReturn(List.of());

            mockMvc.perform(get("/treatments"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));

            verify(queryHandler, times(1)).getAll();
        }
    }

    // ============================================================
    // GET /treatments/{id}
    // ============================================================

    @Nested
    @DisplayName("GET /treatments/{id}")
    class GetTreatmentById {

        @Test
        @DisplayName("Debe devolver 200 con el tratamiento cuando existe")
        void shouldReturnTreatmentWhenExists() throws Exception {
            when(queryHandler.getById(42L)).thenReturn(buildView(42L, "Desparasitación"));

            mockMvc.perform(get("/treatments/42"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.idTreatment").value(42))
                    .andExpect(jsonPath("$.name").value("Desparasitación"));

            verify(queryHandler, times(1)).getById(42L);
        }
    }

    // ============================================================
    // DELETE /treatments/{id}
    // ============================================================

    @Nested
    @DisplayName("DELETE /treatments/{id}")
    class DeactivateTreatment {

        @Test
        @DisplayName("Debe devolver 204 al desactivar correctamente")
        void shouldReturn204WhenDeactivated() throws Exception {
            doNothing().when(deactivateHandler).execute(42L);

            mockMvc.perform(delete("/treatments/42"))
                    .andExpect(status().isNoContent());

            verify(deactivateHandler, times(1)).execute(42L);
        }

        @Test
        @DisplayName("Debe devolver 500 si el handler falla al desactivar")
        void shouldReturn500WhenDeactivateFails() throws Exception {
            doThrow(new IllegalStateException("No se puede desactivar"))
                    .when(deactivateHandler).execute(eq(42L));

            mockMvc.perform(delete("/treatments/42"))
                    .andExpect(status().isInternalServerError());

            verify(deactivateHandler, times(1)).execute(42L);
        }
    }

    // ============================================================
    // Helpers
    // ============================================================

    private TreatmentRequestDTO buildValidRequest() {
        return new TreatmentRequestDTO(
                "Desparasitación canina",
                "Antiparasitario de amplio espectro",
                30,
                Set.of("PERRO"),
                List.of(
                        new TreatmentRequestDTO.ProductLineDTO(1L, 2),
                        new TreatmentRequestDTO.ProductLineDTO(2L, 1)
                )
        );
    }

    private TreatmentView buildView(Long id, String name) {
        return new TreatmentView(
                id,
                name,
                "descripción de ejemplo",
                30,
                true,
                Set.of("PERRO"),
                List.<TreatmentProduct>of(),
                new BigDecimal("150.00")
        );
    }
}