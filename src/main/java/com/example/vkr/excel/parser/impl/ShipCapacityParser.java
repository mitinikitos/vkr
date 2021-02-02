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
            int dedV = intParser(row.getCell(SHIP_CAPACITY_INDEX[0]));
            int passK = intParser(row.getCell(SHIP_CAPACITY_INDEX[1]));
            int passP = intParser(row.getCell(SHIP_CAPACITY_INDEX[2]));
            int gt = intParser(row.getCell(SHIP_CAPACITY_INDEX[3]));
            int nt = intParser(row.getCell(SHIP_CAPACITY_INDEX[4]));
            return new ParseResult<>(new ShipCapacity(dedV, passK, passP, gt, nt), "");
        } catch (Exception e) {
            return new ParseResult<>(new ShipCapacity(), "error in shipCapacity " + row.getRowNum() + "\n");
        }
    }

    @Override
    public void parseToExcel(ShipCapacity shipCapacity, Row row) {
        parser(row.createCell(SHIP_CAPACITY_INDEX[0]), shipCapacity.getDedv());
        parser(row.createCell(SHIP_CAPACITY_INDEX[1]), shipCapacity.getPassK());
        parser(row.createCell(SHIP_CAPACITY_INDEX[2]), shipCapacity.getPassP());
        parser(row.createCell(SHIP_CAPACITY_INDEX[3]), shipCapacity.getGt());
        parser(row.createCell(SHIP_CAPACITY_INDEX[4]), shipCapacity.getNt());
    }
}
