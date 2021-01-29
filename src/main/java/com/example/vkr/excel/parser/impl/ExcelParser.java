package com.example.vkr.excel.parser.impl;

import com.example.vkr.excel.model.ParseModel;
import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.exception.ParseExcelException;
import com.example.vkr.ship.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

    private ExecutorService ex = Executors.newCachedThreadPool();

    @Override
    public ParseResult<ParseModel> parse(Row row) {
        Future<ParseResult<Ship>> futureShip;
        Future<ParseResult<List<Engine>>> futureEngines = null;
        Future<ParseResult<ShipCapacity>> futureCapacity = null;
        Future<ParseResult<ShipDimensions>> futureDimensions = null;
        Future<ParseResult<List<OwnOperator>>> futureOwnOperators = null;
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


//            ship.setOwn(ownOperators.get(0));
            ship.setOwnName(ownOperators.get(0));
//            ship.setOperator(ownOperators.get(1));
            ship.setOperatorName(ownOperators.get(1));
            ship.setShipCapacity(capacity);
            ship.setShipDimensions(dimensions);
            ship.setShipEngine(shipEngine);

            err.append(capacityResult.getError()).append(dimensionsResult.getError());
            ParseModel parseModel = new ParseModel(ship, engines, capacity, dimensions, ownOperatorMap);
            return new ParseResult<>(parseModel, err.toString());
        } catch (ParseExcelException e) {
            return new ParseResult<>(null, e.getMessage());
        } catch (Exception e) {
            return new ParseResult<>(null, e.getMessage());
        }
//        finally {
//            if (futureCapacity != null)
//                futureCapacity.cancel(true);
//            if (futureDimensions != null)
//                futureDimensions.cancel(true);
//            if (futureEngines != null)
//                futureEngines.cancel(true);
//            if (futureOwnOperators != null)
//                futureOwnOperators.cancel(true);
//        }
    }

    @Override
    public void parseToExcel(ParseModel parseModel, Row row) {

    }
}
