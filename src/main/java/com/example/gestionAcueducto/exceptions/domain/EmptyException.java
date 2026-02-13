package com.example.gestionAcueducto.exceptions.domain;

import com.example.gestionAcueducto.exceptions.BaseException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyException extends BaseException {
    public EmptyException(String message) {
        super(message);
    }
}
