package com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.happypets.app_veterinaria_backend.common.infrastructure.config.AbstractControllerTest;
import com.happypets.app_veterinaria_backend.vaccinationcard.application.command.create.CreateVaccinationCardHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.application.query.getbyid.GetVaccinationCardByPetHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.dto.VaccinationCardResponseDTO;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.mapper.VaccinationCardMapper;
import com.happypets.app_veterinaria_backend.vaccinationcard.application.query.report.GetVaccinationCardReportHandler;
import com.happypets.app_veterinaria_backend.vaccinationcard.infrastructure.pdf.VaccinationCardPdfGenerator;
import com.happypets.app_veterinaria_backend.vaccinationcard.domain.model.VaccinationCardReport;

@WebMvcTest(VaccinationCardController.class)
@AutoConfigureMockMvc(addFilters = false)
class VaccinationCardControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateVaccinationCardHandler crearHandler;

    @MockitoBean
    private GetVaccinationCardByPetHandler obtenerPorMascotaHandler;

    @MockitoBean
    private VaccinationCardMapper mapper;

    @MockitoBean
    private GetVaccinationCardReportHandler reportHandler;

    @MockitoBean
    private VaccinationCardPdfGenerator pdfGenerator;


    private VaccinationCardResponseDTO crearDtoRespuesta() {
        return VaccinationCardResponseDTO.builder()
                .idCard(1L)
                .idPet(1L)
                .creationDate(LocalDate.of(2026, 10, 9))
                .status(true)
                .build();
    }

    @Test
    @DisplayName("POST /vaccination-cards/pet/{idPet} devuelve 201 y crea el carnet")
    void debeCrearCarnetParaMascota() throws Exception {
        VaccinationCardResponseDTO respuesta = crearDtoRespuesta();
        when(mapper.toResponse(any())).thenReturn(respuesta);

        mockMvc.perform(post("/vaccination-cards/pet/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCard").value(1))
                .andExpect(jsonPath("$.idPet").value(1));

        // Si tu controller devuelve header Location, descomenta:
        // .andExpect(header().string("Location", "/vaccination-cards/pet/1"));

        verify(crearHandler).execute(1L);
    }

    @Test
    @DisplayName("GET /vaccination-cards/pet/{idPet} devuelve 200 con el carnet")
    void debeObtenerCarnetPorMascota() throws Exception {
        VaccinationCardResponseDTO respuesta = crearDtoRespuesta();
        when(mapper.toResponse(any())).thenReturn(respuesta);

        mockMvc.perform(get("/vaccination-cards/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCard").value(1))
                .andExpect(jsonPath("$.idPet").value(1));

        verify(obtenerPorMascotaHandler).execute(1L);
    }

    @Test
    @DisplayName("Un idPet no numérico devuelve 400 y no llama al handler")
    void debeRechazarIdMascotaInvalido() throws Exception {
        mockMvc.perform(get("/vaccination-cards/pet/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(obtenerPorMascotaHandler, mapper);
    }

    @Test
    @DisplayName("GET /vaccination-cards/pet/{idPet}/pdf devuelve 200 con el PDF como adjunto")
    void debeDescargarCarnetEnPdf() throws Exception {
        VaccinationCardReport report = new VaccinationCardReport(1L, LocalDate.of(2026, 10, 9), 1L,
                "Firulais", "Perro", "Labrador", 3, 25.5, java.util.List.of());
        byte[] fakePdf = "%PDF-1.4 test".getBytes();
        when(reportHandler.execute(1L)).thenReturn(report);
        when(pdfGenerator.generate(report)).thenReturn(fakePdf);

        mockMvc.perform(get("/vaccination-cards/pet/1/pdf"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"))
                .andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString("attachment")));

        verify(reportHandler).execute(1L);
    }
}
