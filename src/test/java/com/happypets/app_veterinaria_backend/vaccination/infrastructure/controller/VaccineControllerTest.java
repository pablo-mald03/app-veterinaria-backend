package com.happypets.app_veterinaria_backend.vaccination.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.domain.pagination.PaginationResult;
import com.happypets.app_veterinaria_backend.common.infrastructure.config.AbstractControllerTest;
import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.command.create.CreateVaccineResponse;
import com.happypets.app_veterinaria_backend.vaccination.application.command.delete.DeleteVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getall.GetAllVaccineRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getall.GetAllVaccineResponse;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getbyid.GetVaccineByIdRequest;
import com.happypets.app_veterinaria_backend.vaccination.application.query.getbyid.GetVaccineByIdResponse;
import com.happypets.app_veterinaria_backend.vaccination.domain.model.Vaccine;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.dto.VaccineResponseDTO;
import com.happypets.app_veterinaria_backend.vaccination.infrastructure.mapper.VaccineMapper;

@WebMvcTest(VaccineController.class)
@AutoConfigureMockMvc(addFilters = false)
class VaccineControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @MockitoBean
    private VaccineMapper vaccineMapper;

    private VaccineResponseDTO crearDtoRespuesta() {
        VaccineResponseDTO dto = new VaccineResponseDTO();
        dto.setIdVaccine(1L);
        dto.setName("Rabia");
        return dto;
    }

    @Test
    @DisplayName("GET /vaccines devuelve 200 con la página de vacunas")
    void debeListarVacunasPaginadas() throws Exception {
        Vaccine vacuna = Vaccine.builder().idVaccine(1L).name("Rabia").build();
        PaginationResult<Vaccine> pagina = new PaginationResult<>(List.of(vacuna), 0, 5, 1, 1);

        GetAllVaccineResponse respuesta = mock(GetAllVaccineResponse.class);
        when(respuesta.getVaccinePage()).thenReturn(pagina);

        doReturn(respuesta).when(mediator).dispatch(any(GetAllVaccineRequest.class));
        when(vaccineMapper.toResponseDTO(any(Vaccine.class))).thenReturn(crearDtoRespuesta());

        mockMvc.perform(get("/vaccines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].idVaccine").value(1));
    }

    @Test
    @DisplayName("GET /vaccines/{id} devuelve 200 con la vacuna")
    void debeObtenerVacunaPorId() throws Exception {
        Vaccine vacuna = Vaccine.builder().idVaccine(1L).name("Rabia").build();

        GetVaccineByIdResponse respuesta = mock(GetVaccineByIdResponse.class);
        when(respuesta.getVaccine()).thenReturn(vacuna);

        doReturn(respuesta).when(mediator).dispatch(any(GetVaccineByIdRequest.class));
        when(vaccineMapper.toResponseDTO(any(Vaccine.class))).thenReturn(crearDtoRespuesta());

        mockMvc.perform(get("/vaccines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVaccine").value(1));
    }

    @Test
    @DisplayName("POST /vaccines devuelve 201 y el header Location")
    void debeGuardarVacuna() throws Exception {
        Vaccine vacuna = Vaccine.builder().idVaccine(1L).name("Parvovirus").build();

        CreateVaccineResponse respuesta = mock(CreateVaccineResponse.class);
        when(respuesta.getVaccine()).thenReturn(vacuna);

        when(vaccineMapper.toCreateRequest(any())).thenReturn(mock(CreateVaccineRequest.class));
        doReturn(respuesta).when(mediator).dispatch(any(CreateVaccineRequest.class));

        String cuerpo = """
            {
              "name": "Parvovirus",
              "description": "Vacuna contra parvovirus canino",
              "species": "CANINO",
              "dosesRequired": 3,
              "intervalDays": 21,
              "status": true
            }
            """;

        mockMvc.perform(post("/vaccines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/vaccines/1"));
    }

    @Test
    @DisplayName("DELETE /vaccines/{id} devuelve 202 y despacha la desactivación")
    void debeDesactivarVacuna() throws Exception {
        mockMvc.perform(delete("/vaccines/1"))
                .andExpect(status().isAccepted());

        verify(mediator).dispatchAsync(any(DeleteVaccineRequest.class));
    }
}