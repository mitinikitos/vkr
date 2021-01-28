package com.example.vkr.excel.parser.impl;


import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ExcelParserBase;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.ship.model.OwnOperator;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ownOperatorParser")
public class OwnOperatorParser extends ExcelParserBase implements ParserBase<List<OwnOperator>> {

    @Override
    public ParseResult<List<OwnOperator>> parse(Row row) {
        OwnOperator own = parseOwnOperator(row, 0);
        OwnOperator operator = parseOwnOperator(row, 5);
        List<OwnOperator> ownOperators = new ArrayList<>();
        ownOperators.add(own);
        ownOperators.add(operator);
        return new ParseResult<>(ownOperators);
    }

    private OwnOperator parseOwnOperator(Row row, int index) {
        try {
            String name = idOwnOperator(row.getCell(OWN_INDEXS[0] + index));
            String address = textParser(row.getCell(OWN_INDEXS[1] + index));
            String[] phones = phonesParser(row.getCell(OWN_INDEXS[2] + index));
            String email = textParser(row.getCell(OWN_INDEXS[3] + index));
            String[] fax = phonesParser(row.getCell(OWN_INDEXS[4] + index));

            return new OwnOperator(name, address, phones, email, fax);
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void parseToExcel(List<OwnOperator> ownOperators, Row row) {

    }
}
