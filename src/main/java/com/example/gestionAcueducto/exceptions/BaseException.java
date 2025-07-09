package com.example.gestionAcueducto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String reason, Throwable throwable) {
        super(reason, throwable);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    // This will simplify our work when inside the controllerAdvice
    public HttpStatus getStatus() {
        var annotation = getClass().getAnnotation(ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }


}