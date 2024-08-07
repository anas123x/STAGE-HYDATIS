package com.example.employepoc.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler;

    public CustomTokenAuthenticationEntryPoint(TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler) {
        this.tokenAuthenticationFailureHandler = tokenAuthenticationFailureHandler;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    }
}
