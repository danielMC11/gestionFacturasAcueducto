package com.example.gestionAcueducto.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // System Exceptions

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        String errorMessage = "El cuerpo de la solicitud está mal formado o contiene datos inválidos.";
        if (ex.getCause() instanceof InvalidFormatException ife) {
            errorMessage = String.format("El valor '%s' no es válido para el tipo esperado '%s' en el campo '%s'.",
                    ife.getValue(), ife.getTargetType().getSimpleName(), ife.getPathReference());
            // 2. Loggear la excepción específica con detalles
            logger.warn("Solicitud con formato inválido: {}", errorMessage, ex);
        } else if (ex.getCause() instanceof JsonParseException) {
            errorMessage = "El formato JSON de la solicitud es inválido.";
            // 3. Loggear la excepción específica con detalles
            logger.warn("Solicitud con JSON inválido: {}", errorMessage, ex);
        } else {
            // 4. Loggear la excepción genérica de HttpMessageNotReadableException
            logger.warn("Error HttpMessageNotReadableException: {}", errorMessage, ex);
        }


        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus,  errorMessage, request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(
            NoHandlerFoundException ex, HttpServletRequest request) {

        String errorMessage = String.format("El recurso solicitado '%s' no fue encontrado.", ex.getRequestURL());

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus, errorMessage, ex.getRequestURL());

        // 5. Loggear este error como INFO o WARN, ya que es un error del cliente (recurso no existe)
        logger.warn("Recurso no encontrado: {}. URI: {}", errorMessage, request.getRequestURI());


        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        String errorMessage = String.format("El método '%s' no está soportado para este recurso. Métodos permitidos: %s",
                ex.getMethod(),
                ex.getSupportedHttpMethods() != null ? ex.getSupportedHttpMethods() : "N/A");

        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus,  errorMessage, request.getRequestURI());

        // 6. Loggear este error como WARN, ya que es un error del cliente (método incorrecto)
        logger.warn("Método HTTP no soportado: {}. URI: {}", errorMessage, request.getRequestURI());


        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) { // Usando pattern matching para instanceof
                String fieldName = fieldError.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
                // 7. Loggear errores de validación de campo a nivel DEBUG o INFO
                logger.debug("Error de validación en campo '{}': {}", fieldName, errorMessage);
            } else if (error instanceof ObjectError objectError) { // Manejo de errores a nivel de clase
                String objectName = objectError.getObjectName();
                String errorMessage = error.getDefaultMessage();
                errors.put(objectName, errorMessage); // O podrías usar una clave más genérica como "globalError"
                // 8. Loggear errores de validación a nivel de objeto
                logger.debug("Error de validación global en '{}': {}", objectName, errorMessage);
            }
        });

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        // 9. Loggear un resumen de los errores de validación
        logger.warn("Errores de validación de argumentos: {}", errors);

        return ResponseEntity.status(httpStatus).body(errors);
    }

    //Auth

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {

        String errorMessage = "Credenciales de autenticación inválidas.";
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED; // 401 Unauthorized

        ErrorResponse errorResponse = new ErrorResponse(httpStatus, errorMessage, request.getRequestURI());

        logger.warn("Intento de autenticación fallido por credenciales inválidas. URI: {}", request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }



    // Domain Exceptions

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(),  ex.getMessage(), request.getRequestURI());
        // 10. Loggear excepciones de dominio (custom) con el nivel de error apropiado
        // Si BaseException es un error de negocio esperado (ej. usuario no encontrado), quizás WARN sea suficiente
        // Si indica un fallo en la lógica de negocio, ERROR es mejor.
        logger.error("Excepción de dominio: {} - Código: {}. URI: {}", ex.getMessage(), ex.getStatus(), request.getRequestURI(), ex);
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    // Catch-All Exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,  HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(httpStatus,  "Ha ocurrido un error inesperado", request.getRequestURI());

        // 11. Loggear la excepción genérica (catch-all) a nivel ERROR, incluyendo el stack trace
        logger.error("Error inesperado en la aplicación. URI: {}", request.getRequestURI(), ex);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}