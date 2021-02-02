package com.example.vkr.excel.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface ExcelService {
    String parser(File file);

//    XSSFWorkbook downloadExcel() throws Exception;
}
