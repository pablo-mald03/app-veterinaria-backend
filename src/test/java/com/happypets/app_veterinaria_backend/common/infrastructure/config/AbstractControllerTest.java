package com.happypets.app_veterinaria_backend.common.infrastructure.config;

import com.happypets.app_veterinaria_backend.common.infrastructure.filters.SessionCookieService;
import com.happypets.app_veterinaria_backend.common.infrastructure.service.JwtService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class AbstractControllerTest {

    @MockitoBean
    protected SessionCookieService sessionCookieService;

    @MockitoBean
    protected JwtService jwtService;
}