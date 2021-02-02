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
            String name = textParser(row.getCell(SHIP_INDEX[0]));
            String type = textParser(row.getCell(SHIP_INDEX[1]));
            String subType = textParser(row.getCell(SHIP_INDEX[2]));
            int regNum = regNumParser(row.getCell(SHIP_INDEX[3]));
            Integer imo = intParser(row.getCell(SHIP_INDEX[4]));
            String callSign = textParser(row.getCell(SHIP_INDEX[5]));
            String project = textParser(row.getCell(SHIP_INDEX[6]));
            String port = textParser(row.getCell(SHIP_INDEX[7]));
            Integer godP = intParser(row.getCell(SHIP_INDEX[8]));
            Integer speed = intParser(row.getCell(SHIP_INDEX[9]));
            return new ParseResult<>(new Ship(regNum, name, type, subType, imo, callSign, project, port, godP, speed), null);
        } catch (Exception e) {
            return new ParseResult<>(null, "error in parser ship " + row.getRowNum() + "\n");
        }
    }

    @Override
    public void parseToExcel(Ship ship, Row row) {
        parser(row.createCell(SHIP_INDEX[0]), ship.getName());
        parser(row.createCell(SHIP_INDEX[1]), ship.getType());
        parser(row.createCell(SHIP_INDEX[2]), ship.getSubType());
        parser(row.createCell(SHIP_INDEX[3]), ship.getId());
        parser(row.createCell(SHIP_INDEX[4]), ship.getImo());
        parser(row.createCell(SHIP_INDEX[5]), ship.getCallSign());
        parser(row.createCell(SHIP_INDEX[6]), ship.getProject());
        parser(row.createCell(SHIP_INDEX[7]), ship.getPort());
        parser(row.createCell(SHIP_INDEX[8]), ship.getGodP());
    }
}
