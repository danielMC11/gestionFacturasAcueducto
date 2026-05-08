package com.altamira.common.exceptions.domain;


import com.altamira.common.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NoRoleFoundException extends BaseException {
    public NoRoleFoundException(String message) {
        super(message);
    }
}
