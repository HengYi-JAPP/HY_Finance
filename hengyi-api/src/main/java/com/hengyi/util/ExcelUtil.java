//package com.hengyi.util;
//
//import com.lanxu.insurance.domain.BudgetdetailBean;
//import com.lanxu.insurance.domain.MaterialcostdetailsBean;
//import com.lanxu.insurance.mapper.FinanceDateMapper;
//import lombok.Setter;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.ss.usermodel.*;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class ExcelUtil {
//
//    @Setter
//    private static FinanceDateMapper financeDateMapper;
//
//    public static void importExcel(File file) throws Exception {
//
//        // 创建文件流对象和工作簿对象
//        FileInputStream in = new FileInputStream(file); // 文件流
//
//        Workbook book = WorkbookFactory.create(in); //工作簿
//
//
//        List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();
//
//        //j: sheet,  i: 行,  k: 列
//
//        //遍历所有的sheet
//        for (int j = 0; j < book.getNumberOfSheets(); j++) {
//
//            // 获取当前excel中sheet的下标：0开始
//            Sheet sheet = book.getSheetAt(j);   // 遍历Sheet
//
//            //遍历所有的行和列
//            for (int i = 0; i < sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//
//                if (i >= 4) {
//                    //一行数据作为一个对象插入
//                    BudgetdetailBean bdb = new BudgetdetailBean();
//
//                    for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
//
//                        if (k > 11) {
//                            //单耗
//                            Cell cell = row.getCell(k);
//                            //单价
//                            Cell cell_price = sheet.getRow(0).getCell(k);
//                            //列名
//                            Cell cell_name = sheet.getRow(3).getCell(k);
//
//                            //一列数据作为一个对象插入
//                            MaterialcostdetailsBean mcdb = new MaterialcostdetailsBean();
//
//                            //名称
//                            mcdb.setMaterialName(changetostring(cell_name));
//                            //单耗
//                            if (isNotEmpty(changetostring(cell))) {
//
//
//                                mcdb.setConsumption(new BigDecimal((changetostring(cell))));
//
//                            }
//
//                            //单价
//                            if (isNotEmpty(changetostring(cell_price))) {
//                                mcdb.setPrice(new BigDecimal(changetostring(cell_price)));
//                            }
//
//
//                            //单位成本
//                            BigDecimal decimal = new BigDecimal(cell.getStringCellValue()).add(new BigDecimal(cell_price.getStringCellValue()));
//
//
//                            //聚酯or纺丝
//
//                            //   mcdb.setField(sheet.getRow(4).getCell(k).getStringCellValue());
//
//
//                            //将该列数据添加进mcdbList集合
//
//                            ArrayList<MaterialcostdetailsBean> mcdbList = bdb.getMaterialcostdetailsBeanArrayList();
//                            mcdbList.add(mcdb);
//                        }
//
//
//                        Cell cell_company = sheet.getRow(i).getCell(0);
//                        Cell cell_month = sheet.getRow(i).getCell(1);
//                        Cell cell_year = sheet.getRow(i).getCell(2);
//                        Cell cell_product = sheet.getRow(i).getCell(3);
//                        Cell cell_workshop = sheet.getRow(i).getCell(4);
//                        Cell cell_line = sheet.getRow(i).getCell(5);
//                        Cell cell_spec = sheet.getRow(i).getCell(6);
//                        Cell cell_yarnkind = sheet.getRow(i).getCell(7);
//                        Cell cell_aarate = sheet.getRow(i).getCell(8);
//                        Cell cell_fsrate = sheet.getRow(i).getCell(9);
//                        Cell cell_dayProduct = sheet.getRow(i).getCell(10);
//                        Cell cell_budgetTotalProduct = sheet.getRow(i).getCell(11);
//
//                        bdb.setCompany(changetostring(cell_company));
//
//                        if (isNotEmpty(changetostring(cell_month))) {
//                            bdb.setMonth(new BigDecimal(changetostring(cell_month)));
//                        }
//                        if (isNotEmpty(changetostring(cell_year))) {
//                            bdb.setYear(new BigDecimal(changetostring(cell_year)));
//                        }
//                        bdb.setProduct(changetostring(cell_product));
//                        bdb.setWorkshop(changetostring(cell_workshop));
//                        bdb.setLine(changetostring(cell_line));
//                        bdb.setSpec(changetostring(cell_spec));
//                        bdb.setYarnkind(changetostring(cell_yarnkind));
//
//                        if (isNotEmpty(changetostring(cell_aarate))) {
//                            bdb.setAarate(new BigDecimal(changetostring(cell_aarate)));
//
//                        }
//                        if (isNotEmpty(changetostring(cell_fsrate))) {
//                            bdb.setFsrate(new BigDecimal(changetostring(cell_fsrate)));
//                        }
//                        if (isNotEmpty(changetostring(cell_dayProduct))) {
//                            bdb.setDayProduct(new BigDecimal(changetostring(cell_dayProduct)));
//                        }
//                        if (isNotEmpty(changetostring(cell_budgetTotalProduct))) {
//                            bdb.setBudgetTotalProduct(new BigDecimal(changetostring(cell_budgetTotalProduct)));
//                        }
//                        //遍历一行中的所有的数据添加进budgetdetailBeanList集合
//
//                        budgetdetailBeanList.add(bdb);
//                    }
//                }
//
//                for (BudgetdetailBean budgetdetailBean : budgetdetailBeanList) {
//                    for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
//                        financeDateMapper.insertmaterialcostdetails(materialcostdetailsBean);
//                    }
//                    financeDateMapper.insertbudgetdetail(budgetdetailBean);
//                }
////            System.out.println(budgetdetailBeanList);
//            }
//        }
//    }
//
//
//    public static Boolean isNotEmpty(String str) {
//        if (str == null || "".equals(str.trim())) {
//            return false;
//        }
//        return true;
//    }
//
//    public static String changetostring(Cell cell) {
//        String strCell = "";
//        if (cell != null) {
//            switch (cell.getCellType()) {
//                case HSSFCell.CELL_TYPE_NUMERIC:
//                    strCell = String.valueOf(cell.getNumericCellValue());
//                    break;
//                case HSSFCell.CELL_TYPE_STRING:
//                    strCell = cell.getStringCellValue();
//                    break;
//                case HSSFCell.CELL_TYPE_BOOLEAN:
//                    strCell = String.valueOf(cell.getBooleanCellValue());
//                    break;
//                default:
//                    strCell = cell.getStringCellValue();
//                    break;
//            }
//            return strCell;
//        } else {
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        File file = new File("E:\\逸鹏预算标准制造费用表-单耗2018.03.20.xlsx");
//        String str = "";
//        if (isNotEmpty(str)) {
//            new BigDecimal(str);
//        }
//        importExcel(file);
//        System.out.println("测试");
//
//    }
//
//
//}
