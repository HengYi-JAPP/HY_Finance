package com.hengyi.job;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.bean.ProductMatchBean;
import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.util.ExcelUtil;
import com.hengyi.util.LoggerUtil;
import com.hengyi.util.StringUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: HJS
 * @Description:EXCEL导出导入功能
 * @Date: 2017/10/31 10:15
 */
@Component
public class ExcelTaskImpl implements ExcelTask{
    @Autowired
    private FinanceDataMapper financeDataMapper;
    /***
     * 导入Excel
     * @throws Exception
     */
//    @Scheduled(cron = "00 47 22 * * ?")
    @Scheduled(cron = "00 57 10 * * ?")
    @Override
    public void importexcel() throws Exception {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoggerUtil.info("开始导入预算数据到budgetdetail表："+df.format(day));
        String path="C:\\Users\\hi60\\Desktop\\finance\\importExcel";
        File directory = new File(path);
        String[] files=directory.list();
        String file="";
        if (files.length>0){
            for (String myFile:files) {
                file=myFile;
            }
        }
        // 创建文件流对象和工作簿对象
        FileInputStream in = new FileInputStream(path+"//"+file); // 文件流
        Workbook book = WorkbookFactory.create(in); //工作簿
        List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();
        //将生产线匹配关系放入集合
        ArrayList<ProductMatchBean> productmatchlist = new ArrayList<>();
        productmatchlist = financeDataMapper.selectproductmatch();
        //j: sheet,  i: 行,  k: 列
        //遍历所有的sheet
        for (int j = 0; j < book.getNumberOfSheets(); j++) {
            // 获取当前excel中sheet的下标：0开始
            Sheet sheet = book.getSheetAt(j);   // 遍历Sheet
            //遍历所有的行和列
            for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
                Row row = sheet.getRow(i);
                if (row==null){
                    continue;
                }
                if (i >= 4&&row.getCell(0)!=null&&!ExcelUtil.changetostring(row.getCell(0),j,i,0).equals("")) {
                    //一行数据作为一个对象插入
                    BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                    budgetdetailBean.setType("预算");
                    for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

                        if (k > 11&&k<69&& row.getCell(k)!=null) {
                            //单耗
                            Cell cell = row.getCell(k);
                            //单价
                            Cell cell_price = sheet.getRow(0).getCell(k);
                            //列名
                            Cell cell_name = sheet.getRow(3).getCell(k);
                            //一列数据作为一个对象插入
                            MaterialcostdetailsBean mcdb = new MaterialcostdetailsBean();
                            //名称
                            mcdb.setMaterialName(ExcelUtil.changetostring(cell_name,j,3,k));
                            //单耗
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell,j,i,k))) {
                                mcdb.setConsumption(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell,j,i,k),j,i,k));
                            }else {
                                continue;
                            }
                            //单价
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price,j,0,k))) {
                                mcdb.setPrice(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_price,j,0,k),j,0,k));
                            }else {
                                continue;
                            }
                            //单位成本
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price,j,i,k)) && StringUtil.isNotEmpty(ExcelUtil.changetostring(cell,j,i,k))) {
                                BigDecimal decimal = ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell,j,i,k),j,i,k).multiply(new BigDecimal(ExcelUtil.changenumbertostring(cell_price,j,0,k)));
                                mcdb.setUnitPrice(decimal);
                            }else {
                                continue;
                            }
                            //聚酯or纺丝
                            //   mcdb.setField(sheet.getRow(4).getCell(k).getStringCellValue());
                            //将该列数据添加进mcdbList集合
                            ArrayList<String> fields=financeDataMapper.selectfieldbymaterialname(mcdb.getMaterialName());
                            if (fields.size()>0){
                                mcdb.setField(fields.get(0));
                                budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(mcdb);}
                        }
                    }
                    Cell cell_company = sheet.getRow(i).getCell(0);
                    Cell cell_month = sheet.getRow(i).getCell(1);
                    Cell cell_year = sheet.getRow(i).getCell(2);
                    Cell cell_product = sheet.getRow(i).getCell(3);
                    Cell cell_workshop = sheet.getRow(i).getCell(4);
                    Cell cell_line = sheet.getRow(i).getCell(5);
                    Cell cell_spec = sheet.getRow(i).getCell(6);
                    Cell cell_yarnkind = sheet.getRow(i).getCell(7);
                    Cell cell_aarate = sheet.getRow(i).getCell(8);
                    Cell cell_fsrate = sheet.getRow(i).getCell(9);
                    Cell cell_dayProduct = sheet.getRow(i).getCell(10);
                    Cell cell_budgetTotalProduct = sheet.getRow(i).getCell(11);

                    budgetdetailBean.setCompany(ExcelUtil.changeinttostring(cell_company,j,i,0));

                    if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_month,j,i,1))) {
                        budgetdetailBean.setMonth(ExcelUtil.changeToBigDecimal(ExcelUtil.changeinttostring(cell_month,j,i,1),j,i,1));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_year,j,i,2))) {
                        budgetdetailBean.setYear(ExcelUtil.changeToBigDecimal(ExcelUtil.changeinttostring(cell_year,j,i,2),j,i,2));
                    }
                    budgetdetailBean.setProduct(ExcelUtil.changetostring(cell_product,j,i,3));
                    budgetdetailBean.setWorkshop(ExcelUtil.changetostring(cell_workshop,j,i,4));
                    budgetdetailBean.setLine(ExcelUtil.changeinttostring(cell_line,j,i,5));
                    budgetdetailBean.setSpec(ExcelUtil.changetostring(cell_spec,j,i,6));
                    budgetdetailBean.setYarnkind(ExcelUtil.changetostring(cell_yarnkind,j,i,7));

                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_aarate,j,i,8))) {
                        budgetdetailBean.setAarate(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_aarate,j,i,8),j,i,8));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_fsrate,j,i,9))) {
                        budgetdetailBean.setFsrate(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_fsrate,j,i,9),j,i,9));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_dayProduct,j,i,10))) {
                        budgetdetailBean.setDayProduct(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_dayProduct,j,i,10),j,i,10));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_budgetTotalProduct,j,i,11))) {
                        budgetdetailBean.setBudgetTotalProduct(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_budgetTotalProduct,j,i,11),j,i,11));
                    }
                    String[] lines=new String[1];
                    //遍历一行中的所有的数据添加进budgetdetailBeanList集合
                    for (ProductMatchBean productMatchBean :productmatchlist){
                        if (StringUtil.equals(productMatchBean.getProductLine(),budgetdetailBean.getLine(),productMatchBean.getCompany(),budgetdetailBean.getCompany())){
                            lines=productMatchBean.getProductMatch().split("/");
                        break;
                        }
                    }

                    for (int k =0;k<lines.length;k++){
                        budgetdetailBean.setLine(lines[k]);
                      ArrayList<String>  result= financeDataMapper.selectbudgetdatabybean(budgetdetailBean);
                      if (result.isEmpty()){
                          for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                              financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
                          }
                          financeDataMapper.insertdetail(budgetdetailBean);
//                          System.out.println(budgetdetailBeanList.size());
                          budgetdetailBeanList.add(budgetdetailBean);
                      }
//                      else if (lines.length>1){//如果该生产线是共用的，则修改生产线的值
//                          for (MaterialcostdetailsBean materialcostdetailsBean: budgetdetailBean.getMaterialcostdetailsBeanArrayList()){
//                              financeDataMapper.updatematerialcostdetails(materialcostdetailsBean);
//                          }
//                          financeDataMapper.updatedetail(budgetdetailBean);
//                      }
                    }
                }
            }
        }
        Date day2=new Date();
        LoggerUtil.info("导入数据结束时间："+df.format(day2));
//        for (BudgetdetailBean budgetdetailBean : budgetdetailBeanList) {
//            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
//                financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
//            }
//            financeDataMapper.insertdetail(budgetdetailBean);
//        }
    }

    @Override
//    @Scheduled(cron = "00 00 18 * * ?")
    public void importexcel2() throws Exception {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoggerUtil.info("开始导入预算数据到budgetdetailadd表："+df.format(day));
        String path="C:\\Users\\Administrator\\Desktop\\finance\\importExcel";
        File directory = new File(path);
        String[] files=directory.list();
        String file="";
        if (files.length>0){
            for (String myFile:files) {
                file=myFile;
            }
        }
        // 创建文件流对象和工作簿对象
        FileInputStream in = new FileInputStream(path+"//"+file); // 文件流
        Workbook book = WorkbookFactory.create(in); //工作簿
        List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();
        //将生产线匹配关系放入集合
        ArrayList<ProductMatchBean> productmatchlist = new ArrayList<>();
        productmatchlist = financeDataMapper.selectproductmatch();
        //j: sheet,  i: 行,  k: 列
        //遍历所有的sheet
        for (int j = 0; j < book.getNumberOfSheets(); j++) {
            // 获取当前excel中sheet的下标：0开始
            Sheet sheet = book.getSheetAt(j);   // 遍历Sheet
            //遍历所有的行和列
            for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
                Row row = sheet.getRow(i);
                if (row==null){
                    continue;
                }
                if (i >= 4&&row.getCell(0)!=null&&!ExcelUtil.changetostring(row.getCell(0),j,i,0).equals("")) {
                    //一行数据作为一个对象插入
                    BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                    budgetdetailBean.setType("预算");
                    for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

                        if (k > 11&&k<69&& row.getCell(k)!=null) {
                            //单耗
                            Cell cell = row.getCell(k);
                            //单价
                            Cell cell_price = sheet.getRow(0).getCell(k);
                            //列名
                            Cell cell_name = sheet.getRow(3).getCell(k);
                            //一列数据作为一个对象插入
                            MaterialcostdetailsBean mcdb = new MaterialcostdetailsBean();
                            //名称
                            mcdb.setMaterialName(ExcelUtil.changetostring(cell_name,j,3,k));
                            //单耗
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell,j,i,k))) {
                                mcdb.setConsumption(new BigDecimal((ExcelUtil.changenumbertostring(cell,j,i,k))));
                            }else {
                                continue;
                            }
                            //单价
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price,j,0,k))) {
                                mcdb.setPrice(new BigDecimal(ExcelUtil.changenumbertostring(cell_price,j,0,k)));
                            }else {
                                continue;
                            }
                            //单位成本
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price,j,0,k)) && StringUtil.isNotEmpty(ExcelUtil.changetostring(cell,j,i,k))) {
                                BigDecimal decimal = new BigDecimal(ExcelUtil.changenumbertostring(cell,j,i,k)).multiply(new BigDecimal(ExcelUtil.changenumbertostring(cell_price,j,0,k)));
                                mcdb.setUnitPrice(decimal);
                            }else {
                                continue;
                            }
                            //聚酯or纺丝
                            //   mcdb.setField(sheet.getRow(4).getCell(k).getStringCellValue());
                            //将该列数据添加进mcdbList集合
                            ArrayList<String> fields=financeDataMapper.selectfieldbymaterialname(mcdb.getMaterialName());
                            if (fields.size()>0){
                                mcdb.setField(fields.get(0));
                                budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(mcdb);}
                        }
                    }
                    Cell cell_company = sheet.getRow(i).getCell(0);
                    Cell cell_month = sheet.getRow(i).getCell(1);
                    Cell cell_year = sheet.getRow(i).getCell(2);
                    Cell cell_product = sheet.getRow(i).getCell(3);
                    Cell cell_workshop = sheet.getRow(i).getCell(4);
                    Cell cell_line = sheet.getRow(i).getCell(5);
                    Cell cell_spec = sheet.getRow(i).getCell(6);
                    Cell cell_yarnkind = sheet.getRow(i).getCell(7);
                    Cell cell_aarate = sheet.getRow(i).getCell(8);
                    Cell cell_fsrate = sheet.getRow(i).getCell(9);
                    Cell cell_dayProduct = sheet.getRow(i).getCell(10);
                    Cell cell_budgetTotalProduct = sheet.getRow(i).getCell(11);

                    budgetdetailBean.setCompany(ExcelUtil.changeinttostring(cell_company,j,i,0));

                    if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_month,j,i,1))) {
                        budgetdetailBean.setMonth(new BigDecimal(ExcelUtil.changeinttostring(cell_month,j,i,1)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_year,j,i,2))) {
                        budgetdetailBean.setYear(new BigDecimal(ExcelUtil.changeinttostring(cell_year,j,i,2)));
                    }
                    budgetdetailBean.setProduct(ExcelUtil.changetostring(cell_product,j,i,3));
                    budgetdetailBean.setWorkshop(ExcelUtil.changetostring(cell_workshop,j,i,4));
                    budgetdetailBean.setLine(ExcelUtil.changeinttostring(cell_line,j,i,5));
                    budgetdetailBean.setSpec(ExcelUtil.changetostring(cell_spec,j,i,6));
                    budgetdetailBean.setYarnkind(ExcelUtil.changetostring(cell_yarnkind,j,i,7));

                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_aarate,j,i,8))) {
                        budgetdetailBean.setAarate(new BigDecimal(ExcelUtil.changenumbertostring(cell_aarate,j,i,8)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_fsrate,j,i,9))) {
                        budgetdetailBean.setFsrate(new BigDecimal(ExcelUtil.changenumbertostring(cell_fsrate,j,i,9)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_dayProduct,j,i,10))) {
                        budgetdetailBean.setDayProduct(new BigDecimal(ExcelUtil.changenumbertostring(cell_dayProduct,j,i,10)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_budgetTotalProduct,j,i,11))) {
                        budgetdetailBean.setBudgetTotalProduct(new BigDecimal(ExcelUtil.changenumbertostring(cell_budgetTotalProduct,j,i,11)));
                    }
                    String[] lines=new String[1];
                    //遍历一行中的所有的数据添加进budgetdetailBeanList集合
                    for (ProductMatchBean productMatchBean :productmatchlist){
                        if (StringUtil.equals(productMatchBean.getProductLine(),budgetdetailBean.getLine(),productMatchBean.getCompany(),budgetdetailBean.getCompany())){
                            lines=productMatchBean.getProductMatch().split("/");
                            break;
                        }
                    }

                    for (int k =0;k<lines.length;k++){
                        budgetdetailBean.setLine(lines[k]);
                        ArrayList<String>  result= financeDataMapper.selectbudgetdatabybean2(budgetdetailBean);
                        if (result.isEmpty()){
//                            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
//                                financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
//                            }
                            financeDataMapper.insertdetail2(budgetdetailBean);
//                          System.out.println(budgetdetailBeanList.size());
                            budgetdetailBeanList.add(budgetdetailBean);
                        }
                    }
                }
            }
        }
        Date day2=new Date();
        System.out.println("导入数据结束时间："+df.format(day2));
    }

    /***
     * 导出excel
     * @throws Exception
     */
//    @Override
//    @Scheduled(cron = "00 00 13 * * ?")
    public void exportexcel () throws Exception {
        ArrayList<LinkedHashMap<String, Object>> budgetresult = financeDataMapper.selectproductbudgetdata();
        String filepath = "C:\\Users\\Administrator\\Desktop\\finance\\exportExcel\\导出.xlsx";
        FileOutputStream out=new FileOutputStream(new File(filepath));
        SXSSFWorkbook book=new SXSSFWorkbook(1000);
        System.out.println("开始导出");
        try {
            //添加表头
            int num2 = 0;
            //表头的列标
            int i=0;
            Row row0=book.createSheet().createRow(num2);
            for (String key:budgetresult.get(0).keySet()) {
                if ("id".equals(key)) {
                } else if ("type".equals(key) || "company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) || "FSrate".equals(key) || "day_product".equals(key) || "budget_total_product".equals(key)) {
                    Cell cell=row0.createCell(i++);
                    cell.setCellValue(key);
                }else {
                    Cell cell0=row0.createCell(i++);
                    cell0.setCellValue(key);
                    Cell cell1=row0.createCell(i++);
                    cell1.setCellValue("单位成本");
                    Cell cell2=row0.createCell(i++);
                    cell2.setCellValue("单价");
                    Cell cell3=row0.createCell(i++);
                    cell3.setCellValue("单耗");
                }

            }
            //开始添加数据
            int num3=1;
            for (LinkedHashMap<String,Object> map : budgetresult) {
                Row row=book.getSheetAt(0).createRow(num3++);
                //数据行的列标
                int j =0;
                for (String key : map.keySet()) {
                    if ("id".equals(key)) {
                    } else if ("type".equals(key) || "company".equals(key) || "month".equals(key) || "year".equals(key) ||
                            "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                            || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) || "FSrate".equals(key) || "day_product".equals(key) || "budget_total_product".equals(key)) {
                        Cell cell=row.createCell(j++);
                        if (!StringUtil.isEmpty(map.get(key))){
                            cell.setCellValue(map.get(key).toString());
                        }else {
                            cell.setCellValue("");
                        }
                    } else {
                        if (!StringUtil.isEmpty(map.get(key))){
                            MaterialcostdetailsBean materialcostdetailsBean = financeDataMapper.selectcostdetailbyid(Integer.parseInt(map.get(key).toString()));
                            Cell cell0 = row.createCell(j++);
                            cell0.setCellValue(materialcostdetailsBean.getMaterialName());
                            Cell cell1=row.createCell(j++);
                            cell1.setCellValue(materialcostdetailsBean.getUnitPrice().toString());
                            Cell cell2=row.createCell(j++);
                            cell2.setCellValue(materialcostdetailsBean.getPrice().toString());
                            Cell cell3=row.createCell(j++);
                            cell3.setCellValue(materialcostdetailsBean.getConsumption().toString());
                        }else {
                            Cell cell0 = row.createCell(j++);
                            cell0.setCellValue("0");
                            Cell cell1 = row.createCell(j++);
                            cell1.setCellValue("0");
                            Cell cell2 = row.createCell(j++);
                            cell2.setCellValue("0");
                            Cell cell3 = row.createCell(j++);
                            cell3.setCellValue("0");
                        }
                    }
                }
//                System.out.println(num3);
            }
            book.write(out);
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("结束时间："+df.format(day));
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
