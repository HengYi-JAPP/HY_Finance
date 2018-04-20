package com.hengyi.job;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.bean.ProductMatchBean;
import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.util.ExcelUtil;
import com.hengyi.util.StringUtil;
import org.apache.poi.ss.usermodel.*;
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
    @Scheduled(cron = "00 20 15 * * ?")
    @Override
    public void importexcel() throws Exception {
        System.out.println("开始了");
//        File file = new File("D:\\work\\预算数据整理\\六家公司_预算单耗&单价_20180404中午.xlsx");
        String path="C:\\Users\\Administrator\\Desktop\\finance\\importExcel";
        File directory = new File(path);
        String[] files=directory.list();
        String file="";
        if (files.length>0){
            for (String myFile:files) {
                file=myFile;
            }
        }
        System.out.println("表格读取成功");
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
                if (i >= 4&&row.getCell(0)!=null&&!ExcelUtil.changetostring(row.getCell(0)).equals("")) {
                    //一行数据作为一个对象插入
                    BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                    budgetdetailBean.setType("预算");
                    for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

                        if (k > 11&& row.getCell(k)!=null) {
                            //单耗
                            Cell cell = row.getCell(k);
                            //单价
                            Cell cell_price = sheet.getRow(0).getCell(k);
                            //列名
                            Cell cell_name = sheet.getRow(3).getCell(k);
                            //一列数据作为一个对象插入
                            MaterialcostdetailsBean mcdb = new MaterialcostdetailsBean();
                            //名称
                            mcdb.setMaterialName(ExcelUtil.changetostring(cell_name));
                            //单耗
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell))) {
                                mcdb.setConsumption(new BigDecimal((ExcelUtil.changenumbertostring(cell))));
                            }else {
                                continue;
                            }
                            //单价
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price))) {
                                mcdb.setPrice(new BigDecimal(ExcelUtil.changenumbertostring(cell_price)));
                            }else {
                                continue;
                            }
                            //单位成本
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price)) && StringUtil.isNotEmpty(ExcelUtil.changetostring(cell))) {
                                BigDecimal decimal = new BigDecimal(ExcelUtil.changenumbertostring(cell)).multiply(new BigDecimal(ExcelUtil.changenumbertostring(cell_price)));
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

                    budgetdetailBean.setCompany(ExcelUtil.changeinttostring(cell_company));

                    if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_month))) {
                        budgetdetailBean.setMonth(new BigDecimal(ExcelUtil.changeinttostring(cell_month)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_year))) {
                        budgetdetailBean.setYear(new BigDecimal(ExcelUtil.changeinttostring(cell_year)));
                    }
                    budgetdetailBean.setProduct(ExcelUtil.changetostring(cell_product));
                    budgetdetailBean.setWorkshop(ExcelUtil.changetostring(cell_workshop));
                    budgetdetailBean.setLine(ExcelUtil.changeinttostring(cell_line));
                    budgetdetailBean.setSpec(ExcelUtil.changetostring(cell_spec));
                    budgetdetailBean.setYarnkind(ExcelUtil.changetostring(cell_yarnkind));

                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_aarate))) {
                        budgetdetailBean.setAarate(new BigDecimal(ExcelUtil.changenumbertostring(cell_aarate)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_fsrate))) {
                        budgetdetailBean.setFsrate(new BigDecimal(ExcelUtil.changenumbertostring(cell_fsrate)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_dayProduct))) {
                        budgetdetailBean.setDayProduct(new BigDecimal(ExcelUtil.changenumbertostring(cell_dayProduct)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_budgetTotalProduct))) {
                        budgetdetailBean.setBudgetTotalProduct(new BigDecimal(ExcelUtil.changenumbertostring(cell_budgetTotalProduct)));
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
                          System.out.println(budgetdetailBeanList.size());
                          budgetdetailBeanList.add(budgetdetailBean);
                      }
                    }
                }
            }
        }
        System.out.println("执行结束"+new Date());
//        for (BudgetdetailBean budgetdetailBean : budgetdetailBeanList) {
//            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
//                financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
//            }
//            financeDataMapper.insertdetail(budgetdetailBean);
//        }
    }



    @Override
    @Scheduled(cron = "00 10 21 * * ?")
    public void exportexcel () throws Exception {
        ArrayList<LinkedHashMap<String, Object>> budgetresult = financeDataMapper.selectproductbudgetdata();
        String filepath = "C:\\Users\\Administrator\\Desktop\\finance\\exportExcel\\导出.xlsx";
        File file = new File(filepath);
        FileInputStream in = new FileInputStream(file);
        Workbook book = WorkbookFactory.create(in); //工作簿
        System.out.println("开始导出");
//        FileOutputStream out = null;
        try {
            //添加表头
            int num2 = 0;
            //表头的列标
            int i=0;
            Row row0=book.getSheetAt(0).createRow(num2);
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
                            cell0.setCellValue("");
                            Cell cell1 = row.createCell(j++);
                            cell1.setCellValue("");
                            Cell cell2 = row.createCell(j++);
                            cell2.setCellValue("");
                            Cell cell3 = row.createCell(j++);
                            cell3.setCellValue("");
                        }
                    }
                }
                System.out.println(num3);
            }
            FileOutputStream out = new FileOutputStream(filepath);
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
