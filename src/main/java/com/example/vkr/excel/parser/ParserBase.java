package com.example.vkr.excel.parser;

import com.example.vkr.excel.model.ParseResult;
import org.apache.poi.ss.usermodel.Row;

public interface ParserBase<T> {
    ParseResult<T> parse(Row row);
    void parseToExcel(T t, Row row);
}
