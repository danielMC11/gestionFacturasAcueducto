package com.example.gestionAcueducto.exceptions;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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


    // System Exceptions

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        String errorMessage = "El cuerpo de la solicitud está mal formado o contiene datos inválidos.";
        if (ex.getCause() instanceof InvalidFormatException ife) {
            errorMessage = String.format("El valor '%s' no es válido para el tipo esperado '%s' en el campo '%s'.",
                    ife.getValue(), ife.getTargetType().getSimpleName(), ife.getPathReference());
        } else if (ex.getCause() instanceof JsonParseException) {
            errorMessage = "El formato JSON de la solicitud es inválido.";
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


        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else if (error instanceof ObjectError) { // Manejo de errores a nivel de clase
                String objectName = ((ObjectError) error).getObjectName();
                String errorMessage = error.getDefaultMessage();
                errors.put(objectName, errorMessage); // O podrías usar una clave más genérica como "globalError"
            }
        });

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).body(errors);
    }


    // Domain Exceptions

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(),  ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }


    // Catch-All Exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex,  HttpServletRequest request){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        System.err.println("ERROR MESSAGE: " + ex.getMessage());
        System.err.println("ERROR CAUSE: " + ex.getCause().getMessage());


        ErrorResponse errorResponse = new ErrorResponse(httpStatus,  "Ha ocurrido un error inesperado", request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


}
