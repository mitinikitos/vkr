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

    /**
     * Parse given {@link OwnOperator} to {@link Row}
     * @param row must not be {@literal null}
     * @param index must not be {@literal null}. Values {@link Integer 0,5}.
     * 0 - parse Own, 5 - parse Operator
     * @return {@link OwnOperator}. if parse failed, return {@literal null}
     */
    private OwnOperator parseOwnOperator(Row row, int index) {
        try {
            String name = idOwnOperator(row.getCell(OWN_INDEX[0] + index));
            String address = textParser(row.getCell(OWN_INDEX[1] + index));
            String[] phones = phonesParser(row.getCell(OWN_INDEX[2] + index));
            String email = textParser(row.getCell(OWN_INDEX[3] + index));
            String[] fax = phonesParser(row.getCell(OWN_INDEX[4] + index));

            return new OwnOperator(name, address, phones, email, fax);
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parse given {@link OwnOperator} to {@link Row}
     * @param row must not be {@literal null}
     * @param index must not be {@literal null}. Values {@link Integer 0,5}.
     * 0 - parse Own, 5 - parse Operator
     * @param ownOperator must not be {@literal null}.
     */
    private void parseOwnOperator(Row row, int index, OwnOperator ownOperator) {
        parser(row.createCell(OWN_INDEX[0] + index), ownOperator.getName());
        parser(row.createCell(OWN_INDEX[1] + index), ownOperator.getAddress());
        parser(row.createCell(OWN_INDEX[2] + index), ownOperator.getPhones());
        parser(row.createCell(OWN_INDEX[3] + index), ownOperator.getEmail());
        parser(row.createCell(OWN_INDEX[4] + index), ownOperator.getFax());
    }

    @Override
    public void parseToExcel(List<OwnOperator> ownOperators, Row row) {
        OwnOperator own = ownOperators.get(0);
        OwnOperator operator = ownOperators.get(1);
        if (own != null) {
            parseOwnOperator(row, 0, ownOperators.get(0));
        }
        if (operator != null) {
            parseOwnOperator(row, 5, ownOperators.get(1));
        }
    }
}
