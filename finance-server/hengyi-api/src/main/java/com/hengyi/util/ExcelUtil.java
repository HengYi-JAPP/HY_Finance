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




    public static String changetostring(Cell cell,int sheetNum,int rowNum,int colNum) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    try {
                        strCell = String.valueOf(cell.getNumericCellValue());
                    }catch (Exception e){
                        strCell = "-1";
                        LoggerUtil.error("sheet:"+sheetNum+"，row:"+rowNum+"，col:"+colNum+"出现格式错误");
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    try {
                        strCell = cell.getStringCellValue();
                    }catch (Exception e){
                        strCell = "-1";
                        LoggerUtil.error("sheet:"+sheetNum+"，row:"+rowNum+"，col:"+colNum+"出现格式错误");
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    try {
                        strCell = String.valueOf(cell.getBooleanCellValue());
                    }catch (Exception e){
                        strCell="-1";
                        LoggerUtil.error("sheet:"+sheetNum+"，row:"+rowNum+"，col:"+colNum+"出现格式错误");
                    }
                    break;
                default:
                    try {
                        strCell = cell.getStringCellValue();
                    }catch (Exception e){
                        strCell="-1";
                        LoggerUtil.error("sheet:"+sheetNum+"，row:"+rowNum+"，col:"+colNum+"出现格式错误");
                    }
                    break;
            }
            return strCell;
        } else {
            return "";
        }
    }



    public static String changenumbertostring(Cell cell,int sheetNum,int rowNum,int colNum) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    try {
                        strCell = String.valueOf( cell.getNumericCellValue());
                    }catch (Exception e){
                        strCell = "-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    try {
                        strCell = cell.getStringCellValue();
                    }catch (Exception e){
                        strCell="-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }
                    break;
                default:
                    try {
                        strCell = "0";
                    }catch (Exception e){
                        strCell = "-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }
                    break;
            }
            return strCell;
        } else {
            return "";
        }
    }


    /***
     * 将单元格的类型转化为字符串类型
     * @param cell 单元格
     * @param sheetNum excel的第几个表格
     * @param rowNum 第几行
     * @param colNum 第几列
     * @return
     */
    public static String changeinttostring(Cell cell,int sheetNum,int rowNum,int colNum) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    try {
                        strCell = String.valueOf((int) cell.getNumericCellValue());
                    }catch (Exception e){
                        strCell = "-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }

                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    try {
                        strCell = cell.getStringCellValue();
                    }catch (Exception e){
                        strCell="-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    try {
                        strCell = String.valueOf(cell.getBooleanCellValue());
                    }catch (Exception e){
                        strCell="-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }
                    break;
                default:
                    try {
                        strCell = cell.getStringCellValue();
                    }catch (Exception e){
                        strCell="-1";
                        LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
                    }
                    break;
            }
            return strCell;
        } else {
            return "";
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

    /***
     * 将传入的值转化为bigDecimal，并进行处理，用于Excel格式检查
     * @param value
     * @return 如果为返回-1则为格式错误,即出现形转错误
     */
    public static BigDecimal changeToBigDecimal(String value,int sheetNum,int rowNum,int colNum){
        try {
            BigDecimal bigDecimal=new BigDecimal(value);
            return bigDecimal;
        }catch (Exception e){//如果出现异常就记录下第几个sheet第几行第几列的格式错误
            LoggerUtil.error("sheet:"+sheetNum+",row:"+rowNum+",col:"+colNum+"出现格式错误");
            return new BigDecimal("-1");
        }
    };


}
