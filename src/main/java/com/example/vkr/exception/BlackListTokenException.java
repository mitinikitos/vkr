package com.example.vkr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BlackListTokenException extends Exception {

    public BlackListTokenException(String err) {
        super(err);
    }
}
