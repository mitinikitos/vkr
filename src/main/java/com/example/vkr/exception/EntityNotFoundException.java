package com.example.vkr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND ,"entity not found: " + id);
    }
}
