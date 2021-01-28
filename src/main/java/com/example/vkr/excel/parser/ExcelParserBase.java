package com.example.vkr.excel.parser;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Arrays;

public abstract class ExcelParserBase {

    protected static final int[] SHIP_INDEXS = new int[] { 0, 1, 2, 3, 4, 5, 6, 13 };
    protected static final int[] ENGINE_INDEXS = new int[] { 14, 17, 20 };
    protected static final int[] OWN_INDEXS = new int[] { 31, 32, 33, 34, 35 };
    protected static final int[] SHIP_CAPACITY_INDEXS = new int[] { 7, 8, 9, 10, 11 };
    protected static final int[] SHIP_DIMENSIONS_INDEXS = new int[] { 24, 25, 26, 27, 28, 29 };


    public int regNumParser(Cell cell) throws NumberFormatException, NullPointerException {
        String input = parser(cell);
        return Integer.parseInt(input);
    }

    public String idOwnOperator(Cell cell) throws NullPointerException {
        String input = parser(cell);
        String res = input.trim();
        if (res.toLowerCase().equals("null")) {
            throw new NullPointerException();
        }
        return res;
    }

    public String textParser(Cell cell) {
        try {
            String input = parser(cell);
            return input.trim();
        } catch (Exception e) {
            return null;
        }
    }

    public String[] phonesParser(Cell cell) {
        try {
            String input = parser(cell);
            return Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);
        } catch (NullPointerException e) {
            return new String[]{};
        }
    }

    public Integer intParser(Cell cell) {
        try {
            String input = parser(cell);
            return Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Double doubleParser(Cell cell) {
        try {
            String input = parser(cell);
            return Double.parseDouble(input);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Integer engineParse(Cell cell) throws NullPointerException, NumberFormatException {
        String input = parser(cell);
        int res = Integer.parseInt(input);
        if (res == 0) {
            throw new NullPointerException();
        }
        return res;
    }

    private String parser(Cell cell) {
        StringBuilder res = new StringBuilder();
        try {
            switch (cell.getCellType()) {
                case STRING:
                    res.append(cell.getStringCellValue());
                    break;
                case NUMERIC:
                case FORMULA:
                    res.append((int) cell.getNumericCellValue());
                    break;
                default:
                    break;
            }
            return res.toString();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
