package com.example.vkr.excel.service;

import com.example.vkr.ship.model.Ship;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.List;

public interface ExcelService {
    /**
     * Parse {@link File} to db
     * @param file must not be {@literal null}
     * @return {@link String}. Errors in parse
     */
    String parser(File file);
    /**
     * Creates {@link XSSFWorkbook} for given {@link List}
     * @param idsShip must not be {@literal null}. {@link List} id {@link Ship}
     * @return {@link XSSFWorkbook}
     */
    XSSFWorkbook downloadExcel(List<Integer> idsShip);
}
