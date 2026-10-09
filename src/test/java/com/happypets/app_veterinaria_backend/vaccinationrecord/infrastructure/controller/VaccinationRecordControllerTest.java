package com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.happypets.app_veterinaria_backend.common.infrastructure.config.AbstractControllerTest;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.command.RegisterVaccinationRecordCommand;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.command.RegisterVaccinationRecordHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.query.GetPendingVaccinationRecordsHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.application.query.GetVaccinationRecordsByCardHandler;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.dto.VaccinationRecordResponseDTO;
import com.happypets.app_veterinaria_backend.vaccinationrecord.infrastructure.mapper.VaccinationRecordMapper;

@WebMvcTest(VaccinationRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
class VaccinationRecordControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegisterVaccinationRecordHandler registrarHandler;

    @MockitoBean
    private GetVaccinationRecordsByCardHandler obtenerPorCarnetHandler;

    @MockitoBean
    private GetPendingVaccinationRecordsHandler pendientesHandler;

    @MockitoBean
    private VaccinationRecordMapper mapper;

    private VaccinationRecordResponseDTO crearDtoRespuesta() {
        return VaccinationRecordResponseDTO.builder()
                .idRecord(1L)
                .idCard(1L)
                .idVaccine(1L)
                .idDoctor(1L)
                .doseNumber(1)
                .applicationDate(LocalDate.of(2026, 10, 9))
                .nextDoseDate(LocalDate.of(2026, 10, 30))
                .batchNumber("LOTE-001")
                .notes("Primera dosis aplicada sin reacciones adversas")
                .build();
    }

    @Test
    @DisplayName("POST /vaccination-records devuelve 201 al registrar una aplicación")
    void debeRegistrarAplicacion() throws Exception {
        RegisterVaccinationRecordCommand command = new RegisterVaccinationRecordCommand(
                1L, 1L, 1L, LocalDate.of(2026, 10, 9), null, null
        );
        when(mapper.toCommand(any())).thenReturn(command);

        // ✅ Mockear mapper.toResponse
        when(mapper.toResponse(any())).thenReturn(crearDtoRespuesta());

        String cuerpo = """
        {
          "idCard": 1,
          "idVaccine": 1,
          "idDoctor": 1,
          "applicationDate": "2026-10-09"
        }
        """;

        mockMvc.perform(post("/vaccination-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRecord").value(1))
                .andExpect(jsonPath("$.idCard").value(1))
                .andExpect(jsonPath("$.idVaccine").value(1))
                .andExpect(jsonPath("$.idDoctor").value(1))
                .andExpect(jsonPath("$.doseNumber").value(1))
                .andExpect(jsonPath("$.applicationDate").value("2026-10-09"))
                .andExpect(jsonPath("$.nextDoseDate").value("2026-10-30"))
                .andExpect(jsonPath("$.batchNumber").value("LOTE-001"))
                .andExpect(jsonPath("$.notes").value("Primera dosis aplicada sin reacciones adversas"));

        // ✅ Capturar y verificar el command que recibió el handler
        ArgumentCaptor<RegisterVaccinationRecordCommand> captor =
                ArgumentCaptor.forClass(RegisterVaccinationRecordCommand.class);
        verify(registrarHandler).execute(captor.capture());

        RegisterVaccinationRecordCommand cmd = captor.getValue();
        assertThat(cmd).isNotNull();
        assertThat(cmd.idCard()).isEqualTo(1L);
        assertThat(cmd.idVaccine()).isEqualTo(1L);
        assertThat(cmd.idDoctor()).isEqualTo(1L);
        assertThat(cmd.applicationDate()).isEqualTo(LocalDate.of(2026, 10, 9));
    }

    @Test
    @DisplayName("POST /vaccination-records con JSON malformado devuelve 400")
    void debeRechazarCuerpoMalformado() throws Exception {
        mockMvc.perform(post("/vaccination-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ esto no es json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(registrarHandler, mapper);
    }

    @Test
    @DisplayName("GET /vaccination-records/{idCard} devuelve 200 con la lista")
    void debeListarAplicacionesPorCarnet() throws Exception {
        when(mapper.toResponseList(any())).thenReturn(List.of(crearDtoRespuesta()));

        mockMvc.perform(get("/vaccination-records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].idRecord").value(1))   // ✅ idRecord
                .andExpect(jsonPath("$[0].idCard").value(1))
                .andExpect(jsonPath("$[0].idVaccine").value(1))
                .andExpect(jsonPath("$[0].batchNumber").value("LOTE-001"));

        verify(obtenerPorCarnetHandler).execute(1L);
    }

    @Test
    @DisplayName("GET /vaccination-records/pending usa 7 días por defecto")
    void debeListarPendientesConDiasPorDefecto() throws Exception {
        when(mapper.toResponseList(any())).thenReturn(List.of());

        mockMvc.perform(get("/vaccination-records/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(pendientesHandler).execute(7);
    }

    @Test
    @DisplayName("GET /vaccination-records/pending?days=30 respeta el parámetro")
    void debeListarPendientesConDiasPersonalizados() throws Exception {
        when(mapper.toResponseList(any())).thenReturn(List.of());

        mockMvc.perform(get("/vaccination-records/pending").param("days", "30"))
                .andExpect(status().isOk());

        verify(pendientesHandler).execute(30);
    }
}