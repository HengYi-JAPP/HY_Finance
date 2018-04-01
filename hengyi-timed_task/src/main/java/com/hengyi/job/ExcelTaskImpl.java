package com.hengyi.job;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.bean.ProductMatchBean;
import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.util.ExcelUtil;
import com.hengyi.util.MathUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author: HJS
 * @Description:EXCEL导出导入功能
 * @Date: 2017/10/31 10:15
 */
@Component
public class ExcelTaskImpl implements ExcelTask{
    @Autowired
    private FinanceDataMapper financeDataMapper;
//    @Scheduled(cron = "30 45 17 * * ?")
    public void importexcel() throws Exception {
        System.out.println("开始了");
        File file = new File("C:\\Users\\38521\\Documents\\Tencent Files\\385213918\\FileRecv\\六家公司_预算单耗&单价（修改2018.04.01凌晨）.xlsx");
        // 创建文件流对象和工作簿对象
        FileInputStream in = new FileInputStream(file); // 文件流
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
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
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
                        if (StringUtil.equals(productMatchBean.getProductLine(),budgetdetailBean.getLine())){
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
//        for (BudgetdetailBean budgetdetailBean : budgetdetailBeanList) {
//            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
//                financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
//            }
//            financeDataMapper.insertdetail(budgetdetailBean);
//        }
    }



    @Override
//    @Scheduled(cron = "0 0 12 * * ?")
    public void exportexcel () throws Exception {
        ArrayList<Map<String, Object>> budgetresult = financeDataMapper.selectproductbudgetdata();
        ArrayList<BudgetdetailBean> Budgetdetaillist = new ArrayList<BudgetdetailBean>();
        String filepath="D:\\恒逸工作文档\\财务预算系统\\预算数据导出excel.xlsx";
        File file = new File(filepath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileInputStream in = new FileInputStream(file);
        Workbook book = WorkbookFactory.create(in); //工作簿
        FileOutputStream out=null;
        for (Map<String, Object> singleRecord : budgetresult) {
            BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
            if (singleRecord.get("id") instanceof Integer) {
                budgetdetailBean.setId(new BigDecimal((Integer) singleRecord.get("id")));
            }
            if (singleRecord.get("year") instanceof Integer) {
                budgetdetailBean.setYear(new BigDecimal((Integer) singleRecord.get("year")));
            }
            if (singleRecord.get("month") instanceof Integer) {
                budgetdetailBean.setMonth(new BigDecimal((Integer) singleRecord.get("month")));
            }
            if (singleRecord.get("company") instanceof String) {
                budgetdetailBean.setCompany((String) singleRecord.get("company"));
            }
            if (singleRecord.get("product") instanceof String) {
                budgetdetailBean.setProduct((String) singleRecord.get("product"));
            }
            if (singleRecord.get("line") instanceof String) {
                budgetdetailBean.setLine((String) singleRecord.get("line"));
            }
            if (singleRecord.get("spec") instanceof String) {
                budgetdetailBean.setSpec((String) singleRecord.get("spec"));
            }
            if (singleRecord.get("yarnKind") instanceof String) {
                budgetdetailBean.setYarnkind((String) singleRecord.get("yarnKind"));
            }
            if (singleRecord.get("workshop") instanceof String) {
                budgetdetailBean.setWorkshop((String) singleRecord.get("workshop"));
            }
            if (singleRecord.get("AArate") instanceof Double) {
                budgetdetailBean.setAarate(new BigDecimal((Double) singleRecord.get("AArate")));
            }
            if (singleRecord.get("FSrate") instanceof Double) {
                budgetdetailBean.setFsrate(new BigDecimal((Double) singleRecord.get("FSrate")));
            }
            if (singleRecord.get("day_product") instanceof Double) {
                budgetdetailBean.setDayProduct(new BigDecimal((Double) singleRecord.get("day_product")));
            }
            if (singleRecord.get("budget_total_product") instanceof Double) {
                budgetdetailBean.setBudgetTotalProduct(new BigDecimal((Double) singleRecord.get("budget_total_product")));
            }
            if (singleRecord.get("type") instanceof String) {
                budgetdetailBean.setType((String) singleRecord.get("type"));
            }
            for (Map.Entry<String, Object> entry : singleRecord.entrySet()) {
                if (StringUtil.equals(entry.getKey(), "type")

                        || StringUtil.equals(entry.getKey(), "budget_total_product")
                        || StringUtil.equals(entry.getKey(), "day_product")
                        || StringUtil.equals(entry.getKey(), "FSrate")
                        || StringUtil.equals(entry.getKey(), "AArate")
                        || StringUtil.equals(entry.getKey(), "workshop")
                        || StringUtil.equals(entry.getKey(), "yarnKind")
                        || StringUtil.equals(entry.getKey(), "product")
                        || StringUtil.equals(entry.getKey(), "company")
                        || StringUtil.equals(entry.getKey(), "year")
                        || StringUtil.equals(entry.getKey(), "month")
                        || StringUtil.equals(entry.getKey(), "id")
                        || StringUtil.equals(entry.getKey(), "month")
                        ) {
                    continue;
                }
                if (entry.getValue() instanceof Integer) {

                    MaterialcostdetailsBean materialcostdetailsBean = financeDataMapper.selectcostdetailbyid((Integer) entry.getValue());
                    if (materialcostdetailsBean != null) {
                        materialcostdetailsBean.setField(entry.getKey());
                        budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                    }
                }
            }
            Budgetdetaillist.add(budgetdetailBean);
        }

        try {
            //添加表头
            int num3=0;
            for (BudgetdetailBean budgetdetailBean1 : Budgetdetaillist) {
                if (budgetdetailBean1.getType().equals("实际")) {
                    Row row = book.getSheetAt(0).createRow(num3);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(budgetdetailBean1.getCompany());
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(budgetdetailBean1.getMonth().toString());
                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(budgetdetailBean1.getYear().toString());
                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(budgetdetailBean1.getProduct());
                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(budgetdetailBean1.getWorkshop());
                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(budgetdetailBean1.getLine());
                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue(budgetdetailBean1.getSpec());
                    Cell cell7 = row.createCell(7);
                    cell7.setCellValue(budgetdetailBean1.getYarnkind());
                    if (budgetdetailBean1.getAarate()!= null){
                        Cell cell8 = row.createCell(8);
                        cell8.setCellValue(budgetdetailBean1.getAarate().toString());
                    }
                    if (budgetdetailBean1.getFsrate()!=null){
                        Cell cell9 = row.createCell(9);
                        cell9.setCellValue(budgetdetailBean1.getFsrate().toString());
                    }
                    if (budgetdetailBean1.getDayProduct()!=null){
                        Cell cell10 = row.createCell(10);
                        cell10.setCellValue(budgetdetailBean1.getDayProduct().toString());
                    }
                    if (budgetdetailBean1.getBudgetTotalProduct()!=null){
                        Cell cell11 = row.createCell(11);
                        cell11.setCellValue(budgetdetailBean1.getBudgetTotalProduct().toString());
                    }
                    int num4=12;
                    for (MaterialcostdetailsBean materialcostdetailsBean :budgetdetailBean1.getMaterialcostdetailsBeanArrayList()){
                        Cell cell12 = row.createCell(num4++);
                        cell12.setCellValue(materialcostdetailsBean.getMaterialName());
                        Cell cell13 = row.createCell(num4++);
                        cell13.setCellValue(materialcostdetailsBean.getUnitPrice().toString());
                        Cell cell14 = row.createCell(num4++);
                        cell14.setCellValue(materialcostdetailsBean.getPrice().toString());
                        Cell cell15 = row.createCell(num4++);
                        cell15.setCellValue(materialcostdetailsBean.getConsumption().toString());
                    }
                    num3++;
                }
            }
            out = new FileOutputStream(filepath);
            book.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
