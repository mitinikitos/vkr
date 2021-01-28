package com.example.vkr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BindingException extends Exception {

    public BindingException(String input) {
        super(input);
    }
}
