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
            Integer disp = intParser(row.getCell(SHIP_DIMENSIONS_INDEXS[1]));
            Double length = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEXS[2]));
            Double breadth = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEXS[3]));
            Double depth = doubleParser(row.getCell(SHIP_DIMENSIONS_INDEXS[4]));
            String shipClass = textParser(row.getCell(SHIP_DIMENSIONS_INDEXS[0]));
            return new ParseResult<>(new ShipDimensions(disp, length, breadth, depth, shipClass), "");
        } catch (Exception e) {
            return new ParseResult<>(new ShipDimensions(), "error in shipDimensions " + row.getRowNum() + "\n");
        }
    }

    @Override
    public void parseToExcel(ShipDimensions shipDimensions, Row row) {

    }
}
