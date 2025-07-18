package com.example.gestionAcueducto.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;



@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {


    private final String message;
    private final String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.path = null;
        this.timestamp = LocalDateTime.now();
    }




}
