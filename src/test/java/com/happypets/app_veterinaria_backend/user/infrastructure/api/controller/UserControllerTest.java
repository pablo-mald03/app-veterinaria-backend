package com.happypets.app_veterinaria_backend.user.infrastructure.api.controller;

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
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.assignRole.AssignRolesToUserResponse;
import com.happypets.app_veterinaria_backend.user.application.command.change.ChangeUserStatusRequest;
import com.happypets.app_veterinaria_backend.user.application.command.change.ChangeUserStatusResponse;
import com.happypets.app_veterinaria_backend.user.application.command.recoverPassword.RecoverPasswordRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.register.RegisterUserResponse;
import com.happypets.app_veterinaria_backend.user.application.command.update.UpdateUserRequest;
import com.happypets.app_veterinaria_backend.user.application.command.update.UpdateUserResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getAll.GetAllUsersRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getAll.GetAllUsersResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getById.GetUserByIdRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getById.GetUserByIdResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getProfile.GetProfileRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getProfile.GetProfileResponse;
import com.happypets.app_veterinaria_backend.user.application.query.getRoles.GetUserRolesRequest;
import com.happypets.app_veterinaria_backend.user.application.query.getRoles.GetUserRolesResponse;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.request.*;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.dto.response.*;
import com.happypets.app_veterinaria_backend.user.infrastructure.api.mapper.UserMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest extends AbstractControllerTest {

    // ============================================================
    // CUERPOS JSON — deben coincidir EXACTAMENTE con los DTOs
    // ============================================================

    private static final String CUERPO_REGISTRO = """
            {
              "identification": "12345678",
              "name": "Cristian",
              "firstName": "Perez",
              "userRegistry": "cperez",
              "phone": "+502 1234 5678",
              "rawPassword": "Clave123!",
              "email": "cristian@happypets.com",
              "roleAliases": ["ADMIN"]
            }
            """;

    private static final String CUERPO_RECUPERAR_PASSWORD = """
            {
              "dpi": "12345678",
              "email": "cristian@happypets.com",
              "password": "NuevaClave123!",
              "confirmationPassword": "NuevaClave123!"
            }
            """;

    private static final String CUERPO_ACTUALIZAR = """
            {
              "name": "Cristian Actualizado"
            }
            """;

    private static final String CUERPO_ASIGNAR_ROLES = """
            {
              "roleAliases": ["ADMIN", "VETERINARIO"]
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
    private UserMapper userMapper;

    // ============================================================
    // POST /users/register
    // ============================================================

    @Test
    @DisplayName("POST /users/register devuelve 200 con el usuario registrado")
    void debeRegistrarUsuario() throws Exception {
        when(userMapper.mapToRegisterUserRequest(any(RegisterUserRequestDto.class)))
                .thenReturn(mock(RegisterUserRequest.class));
        doReturn(mock(RegisterUserResponse.class)).when(mediator).dispatch(any(RegisterUserRequest.class));
        when(userMapper.mapToRegisterUserResponseDto(any(RegisterUserResponse.class)))
                .thenReturn(new RegisterUserResponseDto());

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_REGISTRO))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(userMapper).mapToRegisterUserRequest(any(RegisterUserRequestDto.class));
        verify(mediator).dispatch(any(RegisterUserRequest.class));
        verify(userMapper).mapToRegisterUserResponseDto(any(RegisterUserResponse.class));
    }

    @Test
    @DisplayName("POST /users/register con JSON malformado devuelve 400 y no llama al mediator")
    void debeRechazarRegistroMalformado() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ esto no es json"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, userMapper);
    }

    @Test
    @DisplayName("POST /users/register sin campos obligatorios devuelve 400")
    void debeRechazarRegistroSinCamposObligatorios() throws Exception {
        String cuerpoIncompleto = """
                {
                  "name": "Cristian"
                }
                """;

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpoIncompleto))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, userMapper);
    }

    // ============================================================
    // GET /users
    // ============================================================

    @Test
    @DisplayName("GET /users devuelve 200 con la página de usuarios")
    void debeListarUsuariosPaginados() throws Exception {
        doReturn(mock(GetAllUsersResponse.class)).when(mediator).dispatch(any(GetAllUsersRequest.class));
        when(userMapper.toGetAllUsersResponseDto(any(GetAllUsersResponse.class)))
                .thenReturn(new GetAllUsersResponseDto());

        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "id")
                        .param("direction", "asc"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetAllUsersRequest.class));
    }

    // ============================================================
    // POST /users/recover-password
    // ============================================================

    @Test
    @DisplayName("POST /users/recover-password devuelve 200 y despacha la recuperación")
    void debeRecuperarPassword() throws Exception {
        when(userMapper.mapToRecoverPasswordRequest(any(RecoverPasswordRequestDto.class)))
                .thenReturn(mock(RecoverPasswordRequest.class));

        mockMvc.perform(post("/users/recover-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_RECUPERAR_PASSWORD))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(RecoverPasswordRequest.class));
    }

    // ============================================================
    // GET /users/{id}
    // ============================================================

    @Test
    @DisplayName("GET /users/{id} devuelve 200 con el detalle del usuario")
    void debeObtenerUsuarioPorId() throws Exception {
        doReturn(mock(GetUserByIdResponse.class)).when(mediator).dispatch(any(GetUserByIdRequest.class));
        when(userMapper.toUserDetailResponseDto(any(GetUserByIdResponse.class)))
                .thenReturn(new UserDetailResponseDto());

        mockMvc.perform(get("/users/5"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetUserByIdRequest.class));
    }

    @Test
    @DisplayName("GET /users/{id} con id no numérico devuelve 400")
    void debeRechazarIdUsuarioInvalido() throws Exception {
        mockMvc.perform(get("/users/abc"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mediator, userMapper);
    }

    // ============================================================
    // PATCH /users/{id}
    // ============================================================

    @Test
    @DisplayName("PATCH /users/{id} devuelve 200 y pasa el id de la ruta al mapper")
    void debeActualizarUsuario() throws Exception {
        when(userMapper.toUpdateUserRequest(eq(5L), any(UpdateUserRequestDto.class)))
                .thenReturn(mock(UpdateUserRequest.class));
        doReturn(mock(UpdateUserResponse.class)).when(mediator).dispatch(any(UpdateUserRequest.class));
        when(userMapper.toUpdateUserResponseDto(any(UpdateUserResponse.class)))
                .thenReturn(mock(UpdateUserResponseDto.class));   // ← mock, no new

        mockMvc.perform(patch("/users/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_ACTUALIZAR))
                .andExpect(status().isOk());

        verify(userMapper).toUpdateUserRequest(eq(5L), any(UpdateUserRequestDto.class));
        verify(mediator).dispatch(any(UpdateUserRequest.class));
    }

    // ============================================================
    // PUT /users/{id}/roles
    // ============================================================

    @Test
    @DisplayName("PUT /users/{id}/roles devuelve 200 y asigna los roles al usuario de la ruta")
    void debeAsignarRoles() throws Exception {
        when(userMapper.toAssignRolesRequest(eq(5L), any(AssignRolesToUserRequestDto.class)))
                .thenReturn(mock(AssignRolesToUserRequest.class));
        doReturn(mock(AssignRolesToUserResponse.class)).when(mediator).dispatch(any(AssignRolesToUserRequest.class));
        when(userMapper.toAssignRolesResponseDto(any(AssignRolesToUserResponse.class)))
                .thenReturn(mock(AssignRolesToUserResponseDto.class));  // ← mock, no new

        mockMvc.perform(put("/users/5/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_ASIGNAR_ROLES))
                .andExpect(status().isOk());

        verify(userMapper).toAssignRolesRequest(eq(5L), any(AssignRolesToUserRequestDto.class));
    }

    // ============================================================
    // GET /users/{id}/roles
    // ============================================================

    @Test
    @DisplayName("GET /users/{id}/roles devuelve 200 con los roles y permisos")
    void debeObtenerRolesDelUsuario() throws Exception {
        doReturn(mock(GetUserRolesResponse.class)).when(mediator).dispatch(any(GetUserRolesRequest.class));
        when(userMapper.toUserRolesResponseDto(any(GetUserRolesResponse.class)))
                .thenReturn(new UserRolesResponseDto());

        mockMvc.perform(get("/users/5/roles"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetUserRolesRequest.class));
    }

    // ============================================================
    // PATCH /users/{id}/status
    // ============================================================

    @Test
    @DisplayName("PATCH /users/{id}/status devuelve 200 y cambia el estado del usuario de la ruta")
    void debeCambiarEstadoDelUsuario() throws Exception {
        when(userMapper.toChangeUserStatusRequest(eq(5L), any(ChangeStatusUserRequestDto.class)))
                .thenReturn(mock(ChangeUserStatusRequest.class));
        doReturn(mock(ChangeUserStatusResponse.class)).when(mediator).dispatch(any(ChangeUserStatusRequest.class));
        when(userMapper.toChangeStatusUserResponseDto(any(ChangeUserStatusResponse.class)))
                .thenReturn(new ChangeStatusUserResponseDto());

        mockMvc.perform(patch("/users/5/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CUERPO_CAMBIAR_ESTADO))
                .andExpect(status().isOk());

        verify(userMapper).toChangeUserStatusRequest(eq(5L), any(ChangeStatusUserRequestDto.class));
    }

    // ============================================================
    // GET /users/profile
    // ============================================================

    @Test
    @DisplayName("GET /users/profile se resuelve como perfil y no como /users/{id}")
    void debeObtenerPerfilDelUsuarioAutenticado() throws Exception {
        doReturn(mock(GetProfileResponse.class)).when(mediator).dispatch(any(GetProfileRequest.class));
        when(userMapper.toUserProfileResponseDto(any(GetProfileResponse.class)))
                .thenReturn(new UserDetailResponseDto());

        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk());

        verify(mediator).dispatch(any(GetProfileRequest.class));
    }
}