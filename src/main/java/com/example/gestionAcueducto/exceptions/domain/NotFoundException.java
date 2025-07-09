package com.example.gestionAcueducto.exceptions.domain;


import com.example.gestionAcueducto.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }
}
