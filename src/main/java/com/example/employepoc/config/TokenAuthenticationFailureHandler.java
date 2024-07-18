package com.example.employepoc.config;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenAuthenticationFailureHandler {
    public String getUnauthorizedResponse() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getServletPath();

        return "{ \"timestamp\": \"" + LocalDateTime.now() + "\", \"error\": \"" + "Unauthorized" + "\", \"status\": 401 , \"message\": \"" + "Token is missing" + "\", \"path\": \"" + path + "\" }";
    }
}
