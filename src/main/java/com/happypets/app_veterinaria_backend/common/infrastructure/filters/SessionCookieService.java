package com.happypets.app_veterinaria_backend.common.infrastructure.filters;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * Principal component to manage the session cookie
 */
@Component
public class SessionCookieService {

    private static final String COOKIE_NAME = "SESSION_TOKEN";

    /**
     * This method set the session cookie
     *
     */
    public void addSessionCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    /**
     * Delete the cookie
     *
     */
    public void clearSessionCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}