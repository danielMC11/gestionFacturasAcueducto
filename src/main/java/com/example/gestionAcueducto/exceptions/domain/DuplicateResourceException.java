package com.example.gestionAcueducto.exceptions.domain;

import com.example.gestionAcueducto.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends BaseException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
