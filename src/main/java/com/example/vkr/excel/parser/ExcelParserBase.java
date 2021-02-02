package com.example.vkr.excel.parser;

import com.sun.istack.Nullable;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Arrays;

public abstract class ExcelParserBase {

    protected static final int[] SHIP_INDEX = new int[] { 0, 1, 2, 3, 4, 5, 6, 12, 13, 30 };
    protected static final int[] ENGINE_INDEX = new int[] { 14, 17, 20, 23 };
    protected static final int[] OWN_INDEX = new int[] { 31, 32, 33, 34, 35 };
    protected static final int[] SHIP_CAPACITY_INDEX = new int[] { 7, 8, 9, 10, 11 };
    protected static final int[] SHIP_DIMENSIONS_INDEX = new int[] { 24, 25, 26, 27, 28, 29 };

    /**
     * Parse {@link Cell} to {@link com.example.vkr.ship.model.Ship} id
     * @param cell can be {@literal null}.
     * @return {@link com.example.vkr.ship.model.Ship} id
     * @exception NumberFormatException if failed parse to int
     * @exception NullPointerException if given {@link Cell} is {@literal null}
     */
    public int regNumParser(@Nullable Cell cell) throws NumberFormatException, NullPointerException {
        String input = parser(cell);
        return (int) Double.parseDouble(input);
    }

    /**
     * Parse {@link Cell} to {@link com.example.vkr.ship.model.OwnOperator} name.
     * @param cell can be {@literal null}
     * @return {@link com.example.vkr.ship.model.OwnOperator} name.
     * @exception NullPointerException if given {@link Cell} is null or equals {@link String "null"}
     */
    public String idOwnOperator(@Nullable Cell cell) throws NullPointerException {
        String input = parser(cell);
        String res = input.trim();
        if (res.toLowerCase().equals("null")) {
            throw new NullPointerException();
        }
        return res;
    }

    /**
     * Parse {@link Cell} to {@link String}
     * @param cell can be {@literal null}
     * @return parse {@link String} or {@literal null}, if {@link Cell} is null or equals {@link String "null"}
     */
    public String textParser(@Nullable Cell cell) {
        try {
            String input = parser(cell);
            String res = input.trim();
            if (res.toLowerCase().equals("null")) {
                return null;
            }
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse given {@link Cell} to String[] phones
     * @param cell can be {@literal null}
     * @return phones Array or empty array, if {@link Cell} is null
     */
    public String[] phonesParser(@Nullable Cell cell) {
        try {
            String input = parser(cell);
            return Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);
        } catch (NullPointerException e) {
            return new String[]{};
        }
    }

    /**
     * Parse {@link Cell} to {@link Integer}.
     * @param cell can be {@literal null}.
     * @return parse {@link Integer} or {@literal null}, if {@link Cell} is null or {@link Integer#parseInt} failed
     */
    public Integer intParser(@Nullable Cell cell) {
        try {
            String input = parser(cell);
            return (int) Double.parseDouble(input);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Parse {@link Cell} to {@link Double}.
     * @param cell can be {@literal null}.
     * @return parse {@link Double} or {@literal null}, if {@link Cell} is null or {@link Double#parseDouble} failed
     */
    public Double doubleParser(@Nullable Cell cell) {
        try {
            String input = parser(cell);
            return Double.parseDouble(input);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Parse {@link Cell} to {@link com.example.vkr.ship.model.Engine} count or power
     * @param cell can be {@literal null}
     * @return {@link Integer}
     * @exception NullPointerException if {@link Cell} is null or {@link Integer 0}.
     * @exception NumberFormatException if {@link Double#parseDouble} failed
     */
    public Integer engineParse(@Nullable Cell cell) throws NullPointerException, NumberFormatException {
        String input = parser(cell);
        int res = (int) Double.parseDouble(input);
        if (res == 0) {
            throw new NullPointerException();
        }
        return res;
    }

    /**
     * Parse {@link Cell} to {@link String}
     * @param cell can be {@literal null}.
     * @return parse {@link String} or {@literal null}, if given {@link Cell} is null
     */
    protected String parser(@Nullable Cell cell) {
        StringBuilder res = new StringBuilder();
        try {
            switch (cell.getCellType()) {
                case STRING:
                    res.append(cell.getStringCellValue());
                    break;
                case NUMERIC:
                case FORMULA:
                    res.append(cell.getNumericCellValue());
                    break;
                default:
                    break;
            }
            return res.toString();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Parse given {@code T} to {@link Cell}
     * @param cell must not be {@literal null}
     * @param t can be {@literal null}
     * @param <T> type param for parse
     */
    protected <T> void parser(Cell cell, @Nullable T t) {
        try {
            if (t == null) {
                cell.setCellValue("NULL");
                return;
            }
            if (t instanceof String) {
                cell.setCellValue((String) t);

            } else if (t instanceof Integer) {
                cell.setCellValue((Integer) t);
            }
        } catch (NullPointerException ignored) {
        }
    }
}
