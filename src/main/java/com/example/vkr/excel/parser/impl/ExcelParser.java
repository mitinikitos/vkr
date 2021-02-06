package com.example.vkr.excel.parser.impl;

import com.example.vkr.excel.model.ParseModel;
import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.exception.ParseExcelException;
import com.example.vkr.ship.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service("excelParser")
public class ExcelParser implements ParserBase<ParseModel> {

    @Resource(name = "shipParser")
    private ParserBase<Ship> shipParser;
    @Resource(name="enginesParser")
    private ParserBase<List<Engine>> enginesParser;
    @Resource(name="ownOperatorParser")
    private ParserBase<List<OwnOperator>> ownOperatorParser;
    @Resource(name = "capacityParser")
    private ParserBase<ShipCapacity> capacityParser;
    @Resource(name = "dimensionsParser")
    private ParserBase<ShipDimensions> dimensionsParser;

    private final ExecutorService ex = Executors.newCachedThreadPool();

    @Override
    public ParseResult<ParseModel> parse(Row row) {
        Future<ParseResult<Ship>> futureShip;
        Future<ParseResult<List<Engine>>> futureEngines;
        Future<ParseResult<ShipCapacity>> futureCapacity;
        Future<ParseResult<ShipDimensions>> futureDimensions;
        Future<ParseResult<List<OwnOperator>>> futureOwnOperators;
        try {
            StringBuilder err = new StringBuilder();

            futureShip = ex.submit(() -> shipParser.parse(row));
            futureEngines = ex.submit(() -> enginesParser.parse(row));
            futureCapacity = ex.submit(() -> capacityParser.parse(row));
            futureDimensions = ex.submit(() -> dimensionsParser.parse(row));
            futureOwnOperators = ex.submit(() -> ownOperatorParser.parse(row));

            var shipResult = futureShip.get();

            if (shipResult.getT() == null) {
                throw new ParseExcelException(shipResult.getError());
            }
            Ship ship = shipResult.getT();

            var enginesResult = futureEngines.get();
            var capacityResult = futureCapacity.get();
            var dimensionsResult = futureDimensions.get();
            var ownOperatorsResult = futureOwnOperators.get();

            List<Engine> engines = enginesResult.getT();
            ShipCapacity capacity = capacityResult.getT();
            ShipDimensions dimensions = dimensionsResult.getT();
            List<OwnOperator> ownOperators = ownOperatorsResult.getT();

            Map<String, OwnOperator> ownOperatorMap = new HashMap<>();
            ownOperators.stream()
                    .filter(Objects::nonNull)
                    .forEach(ownOperator ->
                            ownOperatorMap.putIfAbsent(ownOperator.getName(), ownOperator));

            ShipEngine shipEngine = new ShipEngine(ship, engines.get(0), engines.get(1), engines.get(2));

            capacity.setShip(ship);
            capacity.setRegNum(ship.getId());
            dimensions.setShip(ship);
            dimensions.setRegNum(ship.getId());

            ship.setOwn(ownOperators.get(0));
            ship.setOperator(ownOperators.get(1));
            ship.setShipCapacity(capacity);
            ship.setShipDimensions(dimensions);
            ship.setShipEngine(shipEngine);

            err.append(capacityResult.getError()).append(dimensionsResult.getError());
            ParseModel parseModel = new ParseModel(ship, ownOperatorMap);
            return new ParseResult<>(parseModel, err.toString());
        } catch (Exception e) {
            return new ParseResult<>(null, e.getMessage());
        }
    }

    @Override
    public void parseToExcel(ParseModel parseModel, Row row) {
        Ship ship = parseModel.getShip();
        ShipCapacity capacity = ship.getShipCapacity();
        ShipDimensions dimensions = ship.getShipDimensions();
        ShipEngine shipEngine = ship.getShipEngine();

        List<Engine> engines = new ArrayList<>();
        engines.add(shipEngine.getEngine1());
        engines.add(shipEngine.getEngine2());
        engines.add(shipEngine.getEngine3());

        List<OwnOperator> ownOperators = new ArrayList<>();
        ownOperators.add(ship.getOwn());
        ownOperators.add(ship.getOperator());


        shipParser.parseToExcel(ship, row);
        capacityParser.parseToExcel(capacity, row);
        dimensionsParser.parseToExcel(dimensions, row);
        enginesParser.parseToExcel(engines, row);
        ownOperatorParser.parseToExcel(ownOperators, row);
    }
}
