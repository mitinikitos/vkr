package com.example.vkr.excel.parser.impl;

import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ExcelParserBase;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.ship.model.Engine;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("enginesParser")
public class EnginesParser extends ExcelParserBase implements ParserBase<List<Engine>> {
    @Override
    public ParseResult<List<Engine>> parse(Row row) {
        Engine engine1 = parseEngine(row, 0);
        Engine engine2 = parseEngine(row, 1);
        Engine engine3 = parseEngine(row, 2);

        List<Engine> engines = new ArrayList<>();
        engines.add(engine1);
        engines.add(engine2);
        engines.add(engine3);

        return new ParseResult<>(engines);
    }

    private Engine parseEngine(Row row, int numDvig) {
        try {
            int eng = engineParse(row.getCell(ENGINE_INDEXS[0] + numDvig));
            int engPwr = engineParse(row.getCell(ENGINE_INDEXS[1] + numDvig));
            String dvig = textParser(row.getCell(ENGINE_INDEXS[2] + numDvig));

            return new Engine(eng, engPwr, dvig);
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void parseToExcel(List<Engine> engines, Row row) {

    }
}
