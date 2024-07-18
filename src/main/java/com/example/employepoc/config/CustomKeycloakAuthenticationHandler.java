package com.example.employepoc.config;

import org.joda.time.LocalDateTime;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomKeycloakAuthenticationHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.getOutputStream().println("{ \"timestamp\": \"" + LocalDateTime.now() + "\", \"error\": \"" + "Unauthorized" + "\", \"status\": 401 , \"message\": \"" + "invalid token" + "\", \"path\": \"" + request.getServletPath() + "\" }");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

