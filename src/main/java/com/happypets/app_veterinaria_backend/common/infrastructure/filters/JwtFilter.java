package com.happypets.app_veterinaria_backend.common.infrastructure.filters;

import com.happypets.app_veterinaria_backend.common.infrastructure.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Class filter of the jwt requests
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {


    /*Attributes for jwt filter */
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private static final String COOKIE_NAME = "SESSION_TOKEN";

    /**
     * Principal filter to verify the jwt authentication
     *
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getTokenFromCookie(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            boolean tokenExpired = jwtService.isTokenExpired(token);
            boolean canBeTokenRenewed = jwtService.canBeTokenRenewed(token);

            /*Remove the cookie from httpOnly*/
            if (tokenExpired && !canBeTokenRenewed) {
                clearSessionCookie(response);
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtService.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            boolean validToken = jwtService.isValidToken(token, userDetails);

            /* If the token doesn't match remove the token*/
            if (!validToken) {
                clearSessionCookie(response);
                filterChain.doFilter(request, response);
                return;
            }

            /* Renew if its expired and can be renewed */
            if (tokenExpired && canBeTokenRenewed) {
                token = jwtService.renewToken(token);
                addSessionCookie(response, token);
            }

            /* Register the  auth in spring configurations */
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                token,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            log.error("Error while processing request: {}", e.getMessage());
            clearSessionCookie(response);
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Principal method to get the token form the cookie
     *
     */
    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

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
     * Delete the cookie (logout)
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
