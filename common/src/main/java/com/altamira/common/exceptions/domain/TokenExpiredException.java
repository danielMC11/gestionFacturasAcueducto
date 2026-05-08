package com.altamira.common.exceptions.domain;

import com.altamira.common.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenExpiredException extends BaseException {

    public TokenExpiredException(String message) {
        super(message);
    }
}
