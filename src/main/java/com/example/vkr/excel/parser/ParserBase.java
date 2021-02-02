package com.example.vkr.excel.parser;

import com.example.vkr.excel.model.ParseResult;
import org.apache.poi.ss.usermodel.Row;

public interface ParserBase<T> {
    /**
     * Parse {@link Row} to {@link T}
     * @param row must not be {@literal null}
     * @return {@link ParseResult}
     */
    ParseResult<T> parse(Row row);
    /**
     * Parse {@link T} to given {@link Row}
     * @param t must not be {@literal null}
     * @param row must not be {@literal null}
     */
    void parseToExcel(T t, Row row);
}
