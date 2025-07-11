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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomUnauthorizedHandler implements AuthenticationEntryPoint {


    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(CustomUnauthorizedHandler.class);

    public CustomUnauthorizedHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        logger.error("ERROR DE AUTENTICACIÓN: {}", authException.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // Código 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String path = request.getRequestURI(); // Obtener la ruta de la solicitud
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Necesitas autenticarte para acceder a este recurso.",
                path
        );
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

}