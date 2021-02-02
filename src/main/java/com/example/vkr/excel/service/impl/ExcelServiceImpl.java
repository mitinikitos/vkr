package com.example.vkr.excel.service.impl;

import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.excel.service.ExcelService;
import com.example.vkr.excel.model.ParseModel;
import com.example.vkr.exception.EntityNotFoundException;
import com.example.vkr.ship.model.*;
import com.example.vkr.ship.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


@Service("excelService")
public class ExcelServiceImpl implements ExcelService {

    private final ExecutorService ex = Executors.newCachedThreadPool();

    @Autowired
    ShipService shipService;
    @Autowired
    OwnOperatorService ownOperatorService;
    @Resource(name = "excelParser")
    private ParserBase<ParseModel> excelParser;


    @Override
    public String parser(File file) {
        StringBuilder res = new StringBuilder();

        try(Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Callable<String>> tasks = new ArrayList<>();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
                final Row row = sheet.getRow(i);
                tasks.add(() -> {
                    ParseResult<ParseModel> result = excelParser.parse(row);
                    ParseModel model;
                    if ((model = result.getT()) == null) {
                        return result.getError();
                    }
                    Ship ship = model.getShip();
                    Map<String, OwnOperator> ownOperators = model.getOwnOperators();
                    try {
                        ownOperatorService.saveAll(new ArrayList<>(ownOperators.values()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        shipService.save(ship);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return result.getError();
                });
            }
            List<Future<String>> answers = ex.invokeAll(tasks);
            for (Future<String> future : answers) {
                try {
                    var resString = future.get();
                    res.append(resString);
                } catch (ExecutionException ignored) {
                }
            }
            return res.toString();
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }

    @Override
    public XSSFWorkbook downloadExcel(List<Integer> idsShip) {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Ships");

        for (int i = 0; i < idsShip.size(); i++) {
            Row row = sheet.createRow(i);
            ParseModel parseModel = new ParseModel();
            try {
                Ship ship = shipService.findById(idsShip.get(i));
                parseModel.setShip(ship);
            } catch (EntityNotFoundException e){
                continue;
            }
            excelParser.parseToExcel(parseModel, row);
        }
        return workbook;
    }
}

