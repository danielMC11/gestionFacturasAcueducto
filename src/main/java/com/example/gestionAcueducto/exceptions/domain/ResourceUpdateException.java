package com.example.gestionAcueducto.exceptions.domain;

import com.example.gestionAcueducto.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceUpdateException extends BaseException {
    public ResourceUpdateException(String message) {
        super(message);
    }

}
