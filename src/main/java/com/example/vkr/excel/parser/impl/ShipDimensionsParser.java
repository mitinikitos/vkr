package com.example.vkr.excel.parser.impl;

import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ExcelParserBase;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.ship.model.ShipDimensions;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service("dimensionsParser")
public class ShipDimensionsParser extends ExcelParserBase implements ParserBase<ShipDimensions> {
    
    @Override
    public ParseResult<ShipDimensions> parse(Row row) {
        try {
            Integer disp = intParser(row.getCell(SHIP_DIMENSIONS_INDEX[1]));
            Double length = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEX[2]));
            Double breadth = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEX[3]));
            Double depth = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEX[4]));
            Double draught = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEX[5]));
            String shipClass = textParser(row.getCell(SHIP_DIMENSIONS_INDEX[0]));
            return new ParseResult<>(new ShipDimensions(disp, length, breadth, depth, draught, shipClass), "");
        } catch (Exception e) {
            return new ParseResult<>(new ShipDimensions(), "error in shipDimensions " + row.getRowNum() + "\n");
        }
    }

    @Override
    public void parseToExcel(ShipDimensions shipDimensions, Row row) {
        parser(row.createCell(SHIP_DIMENSIONS_INDEX[0]), shipDimensions.getShipClass());
        parser(row.createCell(SHIP_DIMENSIONS_INDEX[1]), shipDimensions.getDisp());
        parser(row.createCell(SHIP_DIMENSIONS_INDEX[2]), shipDimensions.getLength());
        parser(row.createCell(SHIP_DIMENSIONS_INDEX[3]), shipDimensions.getBreadth());
        parser(row.createCell(SHIP_DIMENSIONS_INDEX[4]), shipDimensions.getDepth());
        parser(row.createCell(SHIP_DIMENSIONS_INDEX[5]), shipDimensions.getDraught());
    }
}
