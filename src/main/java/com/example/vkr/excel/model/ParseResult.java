package com.example.vkr.excel.model;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParseResult<T> {
    private T t;
    @Nullable
    private String error;

    public ParseResult (T t, String error) {
        this.t = t;
        this.error = error;
    }

    public ParseResult (T t) {
        this.t = t;
    }
}
