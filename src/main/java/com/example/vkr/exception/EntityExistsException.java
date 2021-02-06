package com.example.vkr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityExistsException extends ResponseStatusException {

    public EntityExistsException(String err) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, err);
    }
}
