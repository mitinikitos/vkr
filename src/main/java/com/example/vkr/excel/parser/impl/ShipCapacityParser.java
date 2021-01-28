package com.example.vkr.excel.parser.impl;

import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ExcelParserBase;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.ship.model.ShipCapacity;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service("capacityParser")
public class ShipCapacityParser extends ExcelParserBase implements ParserBase<ShipCapacity> {

    @Override
    public ParseResult<ShipCapacity> parse(Row row) {
        try {
            int dedV = intParser(row.getCell(SHIP_CAPACITY_INDEXS[0]));
            int passK = intParser(row.getCell(SHIP_CAPACITY_INDEXS[1]));
            int passP = intParser(row.getCell(SHIP_CAPACITY_INDEXS[2]));
            int gt = intParser(row.getCell(SHIP_CAPACITY_INDEXS[3]));
            int nt = intParser(row.getCell(SHIP_CAPACITY_INDEXS[4]));
            return new ParseResult<>(new ShipCapacity(dedV, passK, passP, gt, nt), "");
        } catch (Exception e) {
            return new ParseResult<>(new ShipCapacity(), "error in shipCapacity " + row.getRowNum() + "\n");
        }
    }

    @Override
    public void parseToExcel(ShipCapacity shipCapacity, Row row) {

    }
}
