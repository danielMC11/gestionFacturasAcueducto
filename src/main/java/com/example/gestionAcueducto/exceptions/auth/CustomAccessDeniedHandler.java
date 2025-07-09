package com.example.gestionAcueducto.exceptions.auth;

import com.example.gestionAcueducto.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        logger.error("Forbidden error: {}", accessDeniedException.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value()); // Código 403
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String path = request.getRequestURI();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                "No tienes permiso para acceder a este recurso.",
                path
        );
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
