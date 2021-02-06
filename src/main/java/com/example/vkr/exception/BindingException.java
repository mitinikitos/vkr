package com.example.vkr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BindingException extends ResponseStatusException {

    public BindingException(String input) {
        super(HttpStatus.BAD_REQUEST, input);
    }
}
