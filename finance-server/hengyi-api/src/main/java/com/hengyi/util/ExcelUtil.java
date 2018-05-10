package com.hengyi.util;


import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
                case HSSFCell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
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

    /***
     * 提供一个下载方法返回给前端一个io流
     * @param filePath 服务器上的下载路径
     * @param response 返回给前端的响应
     */
    public static void download(String filePath, HttpServletResponse response){
        try {
            File file=new File(filePath);
            if (!file.exists()) {
                response.sendError(404, "File not found!");
            }
            InputStream in=new FileInputStream(file);
            response.reset();
            response.setContentType("application/x-msdownload;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(file.getName().getBytes(), "ISO-8859-1"));
            OutputStream os = response.getOutputStream();
            IOUtils.copy(in,os);
            in.close();
            os.close();
        }catch (Exception e){
            LoggerUtil.error(e.toString());
        }

    }


}
