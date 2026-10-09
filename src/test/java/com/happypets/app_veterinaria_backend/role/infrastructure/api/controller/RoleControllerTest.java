package com.happypets.app_veterinaria_backend.role.infrastructure.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.happypets.app_veterinaria_backend.common.application.mediator.Mediator;
import com.happypets.app_veterinaria_backend.common.infrastructure.config.AbstractControllerTest;
import com.happypets.app_veterinaria_backend.role.application.command.create.CreateRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.create.CreateRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.delete.ChangeStatusRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.delete.ChangeStatusRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.patch.PatchRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.command.patch.PatchRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.command.update.UpdateRolePermissionsRequest;
import com.happypets.app_veterinaria_backend.role.application.command.update.UpdateRolePermissionsResponse;
import com.happypets.app_veterinaria_backend.role.application.query.findById.GetRoleByIdRequest;
import com.happypets.app_veterinaria_backend.role.application.query.findById.GetRoleByIdResponse;
import com.happypets.app_veterinaria_backend.role.application.query.getAll.GetAllRoleRequest;
import com.happypets.app_veterinaria_backend.role.application.query.getAll.GetAllRoleResponse;
import com.happypets.app_veterinaria_backend.role.application.query.rolePermissions.GetRolePermissionsRequest;
import com.happypets.app_veterinaria_backend.role.application.query.rolePermissions.GetRolePermissionsResponse;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.ChangeStatusRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.CreateRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.PatchRoleRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.request.UpdateRolePermissionsRequestDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.ChangeStatusRoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.CreateRoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.GetAllRoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.PatchRoleResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.RoleDetailResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.RolePermissionsResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.dto.response.UpdateRolePermissionsResponseDto;
import com.happypets.app_veterinaria_backend.role.infrastructure.api.mapper.RoleMapper;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest extends AbstractControllerTest {

    // ============================================================
    // CUERPOS JSON — coinciden con los DTOs reales
    // ============================================================

    private static final String CUERPO_CREAR_ROL = """
            {
              "alias": "ADMIN",
              "name": "Administrador",
              "description": "Rol con todos los permisos",
              "permissionIds": [1, 2, 3]
            }
            """;

    private static final String CUERPO_ACTUALIZAR_PERMISOS = """
            {
              "permissionIds": [1, 2]
            }
            """;

    private static final String CUERPO_PATCH_ROL = """
            {
              "alias": "ADMIN_V2",
              "name": "Administrador v2",
              "description": "Descripción actualizada"
            }
            """;

    private static final String CUERPO_CAMBIAR_ESTADO = """
            {
              "status": false
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Mediator mediator;

    @MockitoBean
    private RoleMapper roleMapper;

    // ============================================================
    // POST /roles
    // ============================================================

    @Test
    @DisplayName("POST /roles devuelve 200 y crea el rol")
    void debeCrearRol() throws Exception {
        when(roleMapper.toCreateRequestDto(any(CreateRoleRequestDto.class)))
                .thenReturn(mock(CreateRoleRequest.class));
        doReturn(mock(CreateRoleResponse.class))
                .when(mediator).dispatch(any(CreateRoleRequest.class));
        when(roleMapper.toCreateResponseDto(any(CreateRoleResponse.class)))
                .thenReturn(mock(CreateRoleResponseDto.class));

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_CREAR_ROL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(roleMapper).toCreateRequestDto(any(CreateRoleRequestDto.class));
        verify(mediator).dispatch(any(CreateRoleRequest.class));
        verify(roleMapper).toCreateResponseDto(any(CreateRoleResponse.class));
    }

    @Test
    @DisplayName("POST /roles con JSON malformado devuelve 400 y no llama al mediator")
    void debeRechazarCrearRolMalformado() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ esto no es json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, roleMapper);
    }

    @Test
    @DisplayName("POST /roles sin campos obligatorios devuelve 400")
    void debeRechazarCrearRolSinCamposObligatorios() throws Exception {
        String cuerpoIncompleto = """
                {
                  "name": "Administrador"
                }
                """;

        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpoIncompleto))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, roleMapper);
    }

    // ============================================================
    // GET /roles
    // ============================================================

    @Test
    @DisplayName("GET /roles devuelve 200 con la página de roles")
    void debeListarRolesPaginados() throws Exception {
        doReturn(mock(GetAllRoleResponse.class))
                .when(mediator).dispatch(any(GetAllRoleRequest.class));
        when(roleMapper.toGetAllResponseDto(any(GetAllRoleResponse.class)))
                .thenReturn(mock(GetAllRoleResponseDto.class));

        mockMvc.perform(get("/roles")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetAllRoleRequest.class));
        verify(roleMapper).toGetAllResponseDto(any(GetAllRoleResponse.class));
    }

    // ============================================================
    // GET /roles/{id}/permissions
    // ============================================================

    @Test
    @DisplayName("GET /roles/{id}/permissions devuelve 200 con los permisos del rol")
    void debeObtenerPermisosDelRol() throws Exception {
        GetRolePermissionsResponse response = mock(GetRolePermissionsResponse.class);
        when(response.getRole()).thenReturn(mock(com.happypets.app_veterinaria_backend.role.domain.entity.Role.class));

        doReturn(response).when(mediator).dispatch(any(GetRolePermissionsRequest.class));
        when(roleMapper.toRolePermissionDto(any()))
                .thenReturn(mock(RolePermissionsResponseDto.class));

        mockMvc.perform(get("/roles/5/permissions"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetRolePermissionsRequest.class));
        verify(roleMapper).toRolePermissionDto(any());
    }

    // ============================================================
    // PUT /roles/{id}/permissions
    // ============================================================

    @Test
    @DisplayName("PUT /roles/{id}/permissions devuelve 200 y actualiza los permisos")
    void debeActualizarPermisosDelRol() throws Exception {
        when(roleMapper.toUpdatePermissionsRequest(eq(5L), any(UpdateRolePermissionsRequestDto.class)))
                .thenReturn(mock(UpdateRolePermissionsRequest.class));
        doReturn(mock(UpdateRolePermissionsResponse.class))
                .when(mediator).dispatch(any(UpdateRolePermissionsRequest.class));
        when(roleMapper.toUpdatePermissionsResponseDto(any(UpdateRolePermissionsResponse.class)))
                .thenReturn(mock(UpdateRolePermissionsResponseDto.class));

        mockMvc.perform(put("/roles/5/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_ACTUALIZAR_PERMISOS))
                .andExpect(status().isOk());

        verify(roleMapper).toUpdatePermissionsRequest(eq(5L), any(UpdateRolePermissionsRequestDto.class));
        verify(mediator).dispatch(any(UpdateRolePermissionsRequest.class));
    }

    @Test
    @DisplayName("PUT /roles/{id}/permissions sin permisos devuelve 400")
    void debeRechazarActualizarPermisosSinPermisos() throws Exception {
        String cuerpoVacio = """
                {
                  "permissionIds": []
                }
                """;

        mockMvc.perform(put("/roles/5/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpoVacio))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, roleMapper);
    }

    // ============================================================
    // GET /roles/{id}
    // ============================================================

    @Test
    @DisplayName("GET /roles/{id} devuelve 200 con el detalle del rol")
    void debeObtenerRolPorId() throws Exception {
        doReturn(mock(GetRoleByIdResponse.class))
                .when(mediator).dispatch(any(GetRoleByIdRequest.class));
        when(roleMapper.toRoleDetailResponseDto(any(GetRoleByIdResponse.class)))
                .thenReturn(mock(RoleDetailResponseDto.class));

        mockMvc.perform(get("/roles/5"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetRoleByIdRequest.class));
    }

    @Test
    @DisplayName("GET /roles/{id} con id no numérico devuelve 400")
    void debeRechazarIdRolInvalido() throws Exception {
        mockMvc.perform(get("/roles/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, roleMapper);
    }

    // ============================================================
    // PATCH /roles/{id}/role
    // ============================================================

    @Test
    @DisplayName("PATCH /roles/{id}/role devuelve 200 y actualiza el rol")
    void debeActualizarRol() throws Exception {
        when(roleMapper.toPatchRoleRequest(eq(5L), any(PatchRoleRequestDto.class)))
                .thenReturn(mock(PatchRoleRequest.class));
        doReturn(mock(PatchRoleResponse.class))
                .when(mediator).dispatch(any(PatchRoleRequest.class));
        when(roleMapper.toPatchRoleResponseDto(any(PatchRoleResponse.class)))
                .thenReturn(mock(PatchRoleResponseDto.class));

        mockMvc.perform(patch("/roles/5/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_PATCH_ROL))
                .andExpect(status().isOk());

        verify(roleMapper).toPatchRoleRequest(eq(5L), any(PatchRoleRequestDto.class));
        verify(mediator).dispatch(any(PatchRoleRequest.class));
    }

    @Test
    @DisplayName("PATCH /roles/{id}/role sin alias devuelve 400")
    void debeRechazarActualizarRolSinAlias() throws Exception {
        String cuerpoIncompleto = """
                {
                  "name": "Solo nombre"
                }
                """;

        mockMvc.perform(patch("/roles/5/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpoIncompleto))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, roleMapper);
    }

    // ============================================================
    // PATCH /roles/{id}/status
    // ============================================================

    @Test
    @DisplayName("PATCH /roles/{id}/status devuelve 200 y cambia el estado del rol")
    void debeCambiarEstadoDelRol() throws Exception {
        when(roleMapper.toChangeRoleStatusRequest(eq(5L), any(ChangeStatusRoleRequestDto.class)))
                .thenReturn(mock(ChangeStatusRoleRequest.class));
        doReturn(mock(ChangeStatusRoleResponse.class))
                .when(mediator).dispatch(any(ChangeStatusRoleRequest.class));
        when(roleMapper.toChangeRoleStatusResponseDto(any(ChangeStatusRoleResponse.class)))
                .thenReturn(mock(ChangeStatusRoleResponseDto.class));

        mockMvc.perform(patch("/roles/5/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_CAMBIAR_ESTADO))
                .andExpect(status().isOk());

        verify(roleMapper).toChangeRoleStatusRequest(eq(5L), any(ChangeStatusRoleRequestDto.class));
        verify(mediator).dispatch(any(ChangeStatusRoleRequest.class));
    }

    @Test
    @DisplayName("PATCH /roles/{id}/status sin status devuelve 400")
    void debeRechazarCambiarEstadoSinStatus() throws Exception {
        String cuerpoVacio = """
                {
                }
                """;

        mockMvc.perform(patch("/roles/5/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpoVacio))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, roleMapper);
    }
}