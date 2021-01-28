package com.example.vkr.excel.service.impl;

import com.example.vkr.excel.model.ParseResult;
import com.example.vkr.excel.parser.ParserBase;
import com.example.vkr.excel.service.ExcelService;
import com.example.vkr.excel.model.ParseModel;
import com.example.vkr.exception.ParseExcelException;
import com.example.vkr.ship.model.*;
import com.example.vkr.ship.service.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


@Service
public class ExcelServiceImpl implements ExcelService {

    private static final String FILE_NAME = "/home/nikita/IdeaProjects/excel/saveFile.xlsx";
    private ExecutorService ex = Executors.newCachedThreadPool();

    @Autowired
    ShipService shipService;
    @Autowired
    EngineService engineService;
    @Autowired
    OwnOperatorService ownOperatorService;
//    @Resource(name = "shipParser")
//    private ParserBase<Ship> shipParser;
//    @Resource(name="enginesParser")
//    private ParserBase<List<Engine>> enginesParser;
//    @Resource(name="ownOperatorParser")
//    private ParserBase<List<OwnOperator>> ownOperatorParser;
//    @Resource(name = "capacityParser")
//    private ParserBase<ShipCapacity> capacityParser;
//    @Resource(name = "dimensionsParser")
//    private ParserBase<ShipDimensions> dimensionsParser;
    @Resource(name = "excelParser")
    private ParserBase<ParseModel> excelParser;


//TODO
//    @Override
//    public String parseEx(File file) {
//        StringBuilder res = new StringBuilder();
//
//        try (Workbook workbook = WorkbookFactory.create(new File(FILE_NAME))){
//            Sheet sheet = workbook.getSheetAt(0);
////            Row rows = null;
////            Future<Ship> shipFuture;
////            Future<List<Engine>> engineFuture;
////            Future<List<OwnOperator>> ownOperatorFuture;
////TODO
//            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//                try {
//                    Row row = sheet.getRow(i);
//                    ParseModel parseModel = excelParser.parseExcelToDb(row);
//                    if (parseModel.getShip() == null) {
//                        throw new ParseExcelException(parseModel.getError());
//                    }
//                    List<Engine> engines = engineService.saveAll(parseModel.getEngines());
//
//                    List<OwnOperator> ownOperators = new ArrayList<>();
//                    ownOperators.add(parseModel.getOperator());
//                    ownOperators.add(parseModel.getOwn());
//                    ownOperatorService.saveAll(ownOperators);
//
//                    Ship ship = parseModel.getShip();
//
//                    ShipEngine shipEngine = new ShipEngine(ship.getId(), engines.get(0),
//                            engines.get(1), engines.get(2));
//
//                    ship.setShipEngine(shipEngine);
//
//
//                    shipService.save(ship);
//
////                    shipCapacityService.save(parseModel.getShipCapacity());
////                    shipDimensionsService.save(parseModel.getShipDimensions());
////                    shipEngineService.save(parseModel.getShip(), engines);
//
//                    if (parseModel.getError() != null)
//                        res.append(parseModel.getError()).append("\r\n");
////                    engineFuture =  ex.submit(() -> {
////                        List<Engine> listEngines = engineService.excelParse(row);
////                        return engineService.saveAll(listEngines);
////                    });
////                    ownOperatorFuture = ex.submit(() -> {
////                        List<OwnOperator> listOwnOperators = ownOperatorService.excelParse(row);
////                        ownOperatorService.saveAll(listOwnOperators);
////                        return listOwnOperators;
////                    });
////
////                    List<Engine> engines = engineFuture.get();
////                    List<OwnOperator> ownOperators = ownOperatorFuture.get();
////
////                    shipFuture = ex.submit(() -> {
////                        ParseResult<Ship> result = excelParser.parseShip(row);
////                        if (result.getError() != null) {
////                            throw new ParseExcelException(result.getError());
////                        }
////                        Ship futureShip = result.getT();
////                        futureShip.setOwn(ownOperators.get(0));
////                        futureShip.setOperator(ownOperators.get(1));
////                        shipService.save(futureShip);
////                        return futureShip;
////                    });
////
////                    Ship ship = shipFuture.get();
////                    ex.submit(() -> shipEngineService.save(ship, engines));
////                    ex.submit(() -> {
////                        ShipCapacity shipCapacity = shipCapacityService.excelParse(row);
////                        shipCapacity.setRegNum(ship);
////                        shipCapacityService.save(shipCapacity);
////                    });
////                    ex.submit(() -> {
////                        ShipDimensions shipDimensions = shipDimensionsService.excelParse(row);
////                        shipDimensions.setRegNum(ship);
////                        shipDimensionsService.save(shipDimensions);
////                    });
//
//                } catch (ParseExcelException e) {
//                    res.append(e.getMessage()).append("\r\n");
//                }
//            }
//
//            return res.toString();
//        } catch (IOException e) {
//            return "error";
//        }
//    }

    @Override
    public String parser(File file) {
        StringBuilder res = new StringBuilder();

        try(Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Callable<String>> tasks = new ArrayList<>();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++) {
                final Row row = sheet.getRow(i);
                tasks.add(() -> {
                    ParseResult<ParseModel> result = excelParser.parse(row);
                    ParseModel model;
                    if ((model = result.getT()) == null) {
                        return result.getError();
                    }
                    Ship ship = model.getShip();
                    Map<String, OwnOperator> ownOperators = model.getOwnOperators();
                    try {
                        ownOperatorService.saveAll(new ArrayList<>(ownOperators.values()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        shipService.addShip(ship);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return result.getError();
                });
            }
            List<Future<String>> answers = ex.invokeAll(tasks);
            for (Future<String> future : answers) {
                try {
                    var resString = future.get();
                    res.append(resString);
                } catch (ExecutionException ignored) {
                }
            }
            return res.toString();
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }

    @Override
    public String parseExcel(File file) {
        StringBuilder res = new StringBuilder();
        Map<Integer, Ship> shipMap = new HashMap<>();
        Map<String, OwnOperator> ownOperatorMap = new HashMap<>();
//        Set<Engine> engineSet = new HashSet<>();
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row;

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                try {
                    row = sheet.getRow(i);
                    ParseResult<ParseModel> parseModelRes = excelParser.parse(row);
                    ParseModel parseModel;
                    if ((parseModel = parseModelRes.getT()) == null) {
                        throw new ParseExcelException(parseModelRes.getError());
                    }
                    Ship ship = parseModel.getShip();
//                    var ownOperators = parseModel.getOwnOperators();
//                    ownOperatorService.saveAll(parseModel.getOwnOperators());
//                    ex.submit(() -> ownOperatorService.saveAll(parseModel.getOwnOperators()));
                    OwnOperator own = parseModel.getOwnOperators().get(0);
                    OwnOperator operator = parseModel.getOwnOperators().get(1);
//                    List<Engine> engines = parseModel.getEngines();

//                    engineSet.addAll(engines);
                    shipMap.putIfAbsent(ship.getId(), ship);
                    if (own != null)
                        ownOperatorMap.putIfAbsent(own.getName(), own);
                    if (operator != null)
                        ownOperatorMap.putIfAbsent(operator.getName(), operator);

//                    ex.submit(() -> shipService.addShip(ship));
                } catch (ParseExcelException e) {
                    res.append(e.getMessage());
                }
            }
            System.out.println(new Date());
            System.out.println(ownOperatorMap.size());
//            System.out.println(engineSet.size());
            System.out.println(shipMap.size());

            ownOperatorService.saveAll(new ArrayList<>(ownOperatorMap.values()));
//            engineService.saveAll(new ArrayList<>(engineSet));
            shipService.saveAll(new ArrayList<>(shipMap.values()));
            return res.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    //TODO
    @Override
    public String parse(File file) {
        StringBuilder res = new StringBuilder();
/*
        try (Workbook workbook = WorkbookFactory.create(file)){
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                try {
                    Row row = sheet.getRow(i);
                    ParseResult<ParseModel> parseModelRes = excelParser.parse(row);
                    if (parseModelRes.getT() == null) {
                        throw new ParseExcelException(parseModelRes.getError());
                    }
                    ParseModel parseModel = parseModelRes.getT();
                    Ship ship = parseModel.getShip();

                    List<Engine> engines = parseModel.getEngines();
//                    ShipCapacity capacity = parseModel.getShipCapacity();
//                    ShipDimensions dimensions = parseModel.getShipDimensions();
                    Map<String, OwnOperator> ownOperators = parseModel.getOwnOperators();

//                    capacity.setRegNum(ship.getId());
//                    dimensions.setRegNum(ship.getId());

                    List<OwnOperator> ownOperatorList = ownOperatorService.saveAll(ownOperators.values());

                    ship.setOwn(ownOperatorList.get(0));
                    ship.setOperator(ownOperatorList.get(1));
//                    shipCapacityService.save(capacity);
//                    shipDimensionsService.save(dimensions);

                    List<Engine> engineList = engineService.saveAll(engines);
                    ShipEngine shipEngine = new ShipEngine(ship, engineList.get(0), engineList.get(1), engineList.get(2));

                    ship.setShipEngine(shipEngine);

                    shipService.addShip(ship);
//                    shipEngineService.save(ship, engineList);
//                    ship.setShipDimensions(dimensions);
//                    ship.setShipCapacity(capacity);
//                    ship.setOwn(ownOperators.get(0));
//                    ship.setOperator(ownOperators.get(1));
//                    ship.setShipEngine(shipEngine);

                    res.append(parseModelRes.getError());

                } catch (ParseExcelException e) {
                    res.append(e.getMessage()).append("\r\n");
                }
            }
            return res.toString();
        } catch (IOException e) {
            return "err";
        }*/
        return "";
    }
/*
    @Override
    public void parse() {
        try (Workbook workbook = WorkbookFactory.create(new File(FILE_NAME))){
            Sheet sheet = workbook.getSheetAt(0);
            Row row;
            Ship ship;
            ShipCapacity shipCapacity;
            ShipDimensions shipDimensions;
            List<Engine> engines;
            List<OwnOperator> ownOperators;

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                try {
                    row = sheet.getRow(i);

                    ship = shipService.excelParse(row);
                    ownOperators = ownOperatorService.excelParse(row);
                    engines = engineService.excelParse(row);
                    shipCapacity = shipCapacityService.excelParse(row);
                    shipDimensions = shipDimensionsService.excelParse(row);

                    ownOperatorService.saveAll(ownOperators);

                    shipCapacity.setRegNum(ship);
                    shipDimensions.setRegNum(ship);
                    ship.setOwn(ownOperators.get(0));
                    ship.setOperator(ownOperators.get(1));

                    shipService.save(ship);
                    shipCapacityService.save(shipCapacity);
                    shipDimensionsService.save(shipDimensions);
                    engines = engineService.saveAll(engines);

                    shipEngineService.save(ship, engines);
                } catch (Exception e) {
                    System.out.println("ошибка в excel service ");
                    continue;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
//    @Override
//    public XSSFWorkbook downloadExcel() throws Exception {
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            XSSFSheet sheet = workbook.createSheet("first");
//
//            List<Ship> ships = shipService.findAll();
//
//
//
//            int nTreads = 8;
////            parser(1, ships.size() - 1, ships, sheet);
//
//
//            ExecutorService ex = Executors.newFixedThreadPool(nTreads);
//            Collection<Callable<Object>> tasks = new ArrayList<>();
//            CountDownLatch latch = new CountDownLatch(nTreads);
//            parser(1, ships.size() - 1, ships, sheet);
//            /*
//            for (int i = 1; i <= nTreads; i++) {
//                int startRowNum = (i - 1) * (ships.size() / nTreads) + 1;
//                int endRowNum;
//                if (i == nTreads) {
//                    endRowNum = ships.size() - 1;
//                } else {
//                    endRowNum = i * (ships.size() / nTreads);
//                }
//                tasks.add(Executors.callable((PrivilegedAction<?>) () -> {
//                    parser(startRowNum, endRowNum, ships, sheet);
//                    return null;
//                }));
////                List<Ship> parseShip = ships.subList(startRowNum, endRowNum);
////                ex.submit(() -> parser(startRowNum, endRowNum, ships, sheet));
////
//            }
//            */
//
////            ex.invokeAll(tasks);
////            latch.await();
//
//
//            System.out.println(sheet.getPhysicalNumberOfRows());
//            return workbook;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

//    private void parser(int startRowNum, int endRowNum, List<Ship> ships, Sheet sheet) {
//        Ship ship;
//
//        for (int i = startRowNum; i <= endRowNum; i++) {
//            ship = ships.get(i);
//            Row row = sheet.createRow(i);
//            excelParser.parseToExcel(ship, row);
//        }
//    }
}
