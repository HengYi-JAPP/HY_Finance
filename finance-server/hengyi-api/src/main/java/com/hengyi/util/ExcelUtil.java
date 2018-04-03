package com.hengyi.util;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtil {




    public static String changetostring(Cell cell) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    strCell = String.valueOf(cell.getNumericCellValue());

                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    strCell = cell.getStringCellValue();
                    break;
            }
            return strCell;
        } else {
            return null;
        }
    }



    public static String changenumbertostring(Cell cell) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    strCell = String.valueOf( cell.getNumericCellValue());
                    break;
                default:
                    strCell = "0";
                    break;
            }
            return strCell;
        } else {
            return "";
        }
    }




    public static String changeinttostring(Cell cell) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    strCell = String.valueOf((int) cell.getNumericCellValue());

                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    strCell = cell.getStringCellValue();
                    break;
            }
            return strCell;
        } else {
            return null;
        }
    }


}
