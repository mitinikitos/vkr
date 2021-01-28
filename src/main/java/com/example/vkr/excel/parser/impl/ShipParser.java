package com.example.vkr.excel.parser.impl;

import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ExcelParserBase;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.ship.model.Ship;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service("shipParser")
public class ShipParser extends ExcelParserBase implements ParserBase<Ship> {

    @Override
    public ParseResult<Ship> parse(Row row) {
        try {
            String name = textParser(row.getCell(SHIP_INDEXS[0]));
            String type = textParser(row.getCell(SHIP_INDEXS[1]));
            String subType = textParser(row.getCell(SHIP_INDEXS[2]));
            int regNum = regNumParser(row.getCell(SHIP_INDEXS[3]));
            int imo = intParser(row.getCell(SHIP_INDEXS[4]));
            String callSign = textParser(row.getCell(SHIP_INDEXS[5]));
            String project = textParser(row.getCell(SHIP_INDEXS[6]));
            Integer godP = intParser(row.getCell(SHIP_INDEXS[7]));
            return new ParseResult<>(new Ship(regNum, name, type, subType, imo, callSign, project, godP), null);
        } catch (Exception e) {
            return new ParseResult<>(null, "ошибка в parser ship " + row.getRowNum());
        }
    }

    //TODO
    @Override
    public void parseToExcel(Ship ship, Row row) {

    }
}
