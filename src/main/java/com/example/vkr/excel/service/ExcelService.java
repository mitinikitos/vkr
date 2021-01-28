package com.example.vkr.excel.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface ExcelService {
//    String parseEx(File file);
    String parse(File file);
    String parseExcel(File file);
    String parser(File file);
//    void parse();

//    XSSFWorkbook downloadExcel() throws Exception;
}
