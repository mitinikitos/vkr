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

    /**
     * Parse {@link Engine} for the given {@link Row} and number engine
     * @param row must not be {@literal null}.
     * @param numDvig must not be {@literal null}. Number engine
     * @return {@link Engine} or {@literal null}, if engine count is {@link Integer 0}
     */
    private Engine parseEngine(Row row, int numDvig) {
        try {
            int eng = engineParse(row.getCell(ENGINE_INDEX[0] + numDvig));
            int engPwr = engineParse(row.getCell(ENGINE_INDEX[1] + numDvig));
            String dvig = textParser(row.getCell(ENGINE_INDEX[2] + numDvig));

            return new Engine(eng, engPwr, dvig);
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parse given {@link Engine} to {@link Row}
     * @param row must not be {@literal null}
     * @param numDvig must not be {@literal null}. Values in {@link Integer [0, 1, 2] }
     * @param engine can be {@literal null}.
     */
    private void parseEngine(Row row, int numDvig, Engine engine) {
        int eng;
        int pwr;
        String dvig;
        if (engine == null) {
            eng = 0;
            pwr = 0;
            dvig = "null";
        } else {
            eng = engine.getCount();
            pwr = engine.getPwr();
            dvig = engine.getDvig();
        }
        parser(row.createCell(ENGINE_INDEX[0] + numDvig), eng);
        parser(row.createCell(ENGINE_INDEX[1] + numDvig), pwr);
        parser(row.createCell(ENGINE_INDEX[2] + numDvig), dvig);
    }

    @Override
    public void parseToExcel(List<Engine> engines, Row row) {
        int sumPwr = 0;
        for (int i = 0; i < engines.size(); i++) {
            parseEngine(row, i, engines.get(i));
            sumPwr += engines.get(i) == null ? 0 : engines.get(i).getPwr();
        }

        parser(row.createCell(ENGINE_INDEX[3]), sumPwr);
    }
}
