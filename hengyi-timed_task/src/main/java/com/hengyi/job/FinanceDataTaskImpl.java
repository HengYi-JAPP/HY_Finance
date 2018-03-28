package com.hengyi.job;

import com.hengyi.bean.*;
import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.sapmapper.SapDataMapper;
import com.hengyi.util.DateUtil;
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
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author: QZM
 * @Description:基础数据同步定时任务
 * @Date: 2017/10/31 10:15
 */
@Component(value = "financeDataTaskImpl")
public class FinanceDataTaskImpl implements FinanceDataTask {
    @Autowired
    private SapDataMapper sapDataMapper;
    @Autowired
    private FinanceDataMapper financeDataMapper;

    /**
     * @param
     * @return
     * @Transactional(rollbackFor = Exception.class)
     * 从SAP定期同步数据到本系统Mysql数据库,将物料描述分解成生产线、规格、产品等信息
     */
    @Override
    @Scheduled(cron = "15 1 8 * * ?")
    public void productlinedatatask() {
        ArrayList<MaterialOfLineSelectBean> materialoflinelist = new ArrayList<MaterialOfLineSelectBean>();
        ArrayList<BudgetdetailBean> budgetdetailBeanlist = new ArrayList<BudgetdetailBean>();
        ArrayList<MaterialMatchBean> materialmatchlist = new ArrayList<MaterialMatchBean>();
        System.out.println("开始了");
        SapDataMonthBean sapDataMonthBean = DateUtil.getsapdatamonthbeannow();
        financeDataMapper.deletebudgetbymonth(sapDataMonthBean);
        budgetdetailBeanlist.clear();
        materialmatchlist = financeDataMapper.selectmaterialmatch();
        materialoflinelist = financeDataMapper.selectmaterialofline(sapDataMonthBean);

        //单耗单价计算主逻辑   将计算的结果存入budgetdetailBeanlist
        for (MaterialOfLineSelectBean materialOfLineSelectBean : materialoflinelist) {
            boolean budgetdetailBeanexist = false;
            for (MaterialMatchBean materialMatchBean : materialmatchlist) {
                if ((materialMatchBean.getMaterialId() != null && StringUtil.equals(materialOfLineSelectBean.getCostMaterialId(), "00000000" + materialMatchBean.getMaterialId())&&StringUtil.equals(materialOfLineSelectBean.getState(),materialMatchBean.getState()))
                        || (materialMatchBean.getCostId() != null && StringUtil.equals(materialOfLineSelectBean.getCostId(), materialMatchBean.getCostId()) && StringUtil.equals(materialMatchBean.getState(), materialOfLineSelectBean.getState())&&StringUtil.equals(materialOfLineSelectBean.getState(),materialMatchBean.getState()))) {
                    for (BudgetdetailBean budgetdetailBean : budgetdetailBeanlist) {
                        if (StringUtil.equals(budgetdetailBean.getCompany(), materialOfLineSelectBean.getCompany())
                                && StringUtil.equals(budgetdetailBean.getProduct(), materialOfLineSelectBean.getProductName())
                                && StringUtil.equals(budgetdetailBean.getLine(), materialOfLineSelectBean.getProductLine())
                                && StringUtil.equals(budgetdetailBean.getSpec(), materialOfLineSelectBean.getProductSpecifications())
                                && StringUtil.equals(budgetdetailBean.getYarnkind(), materialOfLineSelectBean.getProductYarn())
                                && MathUtil.bigdecimalequals(budgetdetailBean.getMonth(), materialOfLineSelectBean.getMonth())
                                && StringUtil.equals(budgetdetailBean.getType(), "实际")
                                ) {
                            if (budgetdetailBean.getBudgetTotalProduct() != null && materialOfLineSelectBean.getOrderProductQuantity() != null && materialOfLineSelectBean.getOrderProductQuantity().doubleValue() > budgetdetailBean.getBudgetTotalProduct().doubleValue()) {
                                budgetdetailBean.setBudgetTotalProduct(MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")));
                            }
                            boolean materialcostdetailsBeanexist = false;
                            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                                if (StringUtil.equals(materialcostdetailsBean.getMaterialName(), materialMatchBean.getMaterialName())) {

                                    materialcostdetailsBean.setConsumption(MathUtil.add(materialcostdetailsBean.getConsumption(), MathUtil.divide(materialOfLineSelectBean.getCostQuantity(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")))));
                                    materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                                    materialcostdetailsBean.setUnitPrice(MathUtil.add(materialcostdetailsBean.getUnitPrice(), MathUtil.divide(materialOfLineSelectBean.getMoney(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")))));
                                    materialcostdetailsBean.setPrice(MathUtil.divide(materialcostdetailsBean.getUnitPrice(),materialcostdetailsBean.getConsumption()));
                                    materialcostdetailsBeanexist = true;
                                    break;
                                }
                            }
                            if (!materialcostdetailsBeanexist) {
                                MaterialcostdetailsBean materialcostdetailsBean = new MaterialcostdetailsBean();
                                materialcostdetailsBean.setMaterialName(materialMatchBean.getMaterialName());
                                materialcostdetailsBean.setPrice(MathUtil.divide(materialOfLineSelectBean.getMoney(), materialOfLineSelectBean.getCostQuantity()));
                                materialcostdetailsBean.setConsumption(MathUtil.divide(materialOfLineSelectBean.getCostQuantity(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000"))));
                                materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                                materialcostdetailsBean.setUnitPrice(MathUtil.divide(materialOfLineSelectBean.getMoney(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000"))));
                                materialcostdetailsBean.setField(materialMatchBean.getField());
                                budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                            }
                            budgetdetailBeanexist = true;
                            break;
                        }
                    }
                    if (!budgetdetailBeanexist) {
                        BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                        budgetdetailBean.setCompany(materialOfLineSelectBean.getCompany());
                        budgetdetailBean.setProduct(materialOfLineSelectBean.getProductName());
                        budgetdetailBean.setMonth(materialOfLineSelectBean.getMonth());
                        budgetdetailBean.setYear(materialOfLineSelectBean.getYear());
                        budgetdetailBean.setWorkshop(materialOfLineSelectBean.getWorkShop());
                        budgetdetailBean.setType("实际");
                        budgetdetailBean.setBudgetTotalProduct(MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")));
                        budgetdetailBean.setLine(materialOfLineSelectBean.getProductLine());
                        budgetdetailBean.setSpec(materialOfLineSelectBean.getProductSpecifications());
                        budgetdetailBean.setYarnkind(materialOfLineSelectBean.getProductYarn());
                        MaterialcostdetailsBean materialcostdetailsBean = new MaterialcostdetailsBean();
                        materialcostdetailsBean.setMaterialName(materialMatchBean.getMaterialName());
                        materialcostdetailsBean.setPrice(MathUtil.divide(materialOfLineSelectBean.getMoney(), materialOfLineSelectBean.getCostQuantity()));
                        materialcostdetailsBean.setConsumption(MathUtil.divide(materialOfLineSelectBean.getCostQuantity(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000"))));
                        materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                        materialcostdetailsBean.setUnitPrice(MathUtil.divide(materialOfLineSelectBean.getMoney(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000"))));
                        materialcostdetailsBean.setField(materialMatchBean.getField());
                        budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                        budgetdetailBeanlist.add(budgetdetailBean);
                    }
                    break;
                }
            }
        }
        for (BudgetdetailBean budgetdetailBean : budgetdetailBeanlist) {
            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
            }
            financeDataMapper.insertbudgetdetail(budgetdetailBean);
        }
    }


    @Override
    @Scheduled(cron = "10 1 14 * * ?")
    public void companyproductdatatask () throws Exception {
        ArrayList<Map<String, Object>> budgetresult = financeDataMapper.selectbudgetdata();
        ArrayList<BudgetdetailBean> Budgetdetaillist = new ArrayList<BudgetdetailBean>();
        File file = new File("D:\\恒逸工作文档\\财务预算系统\\预算数据导出excel.xlsx");
        FileInputStream in = new FileInputStream(file);
        Workbook book = WorkbookFactory.create(in); //工作簿
        FileOutputStream    out=null;
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
                    if (entry.getKey().equals("mate_pta")){
                        System.out.println(materialcostdetailsBean);
                    }
                    if (materialcostdetailsBean != null) {
                        materialcostdetailsBean.setField(entry.getKey());
                        budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                    }
                }
            }
            Budgetdetaillist.add(budgetdetailBean);
        }
        System.out.println("开始导入EXCEL");
        try {
            //添加表头
            int num3=0;
            for (BudgetdetailBean budgetdetailBean1 : Budgetdetaillist) {
                System.out.println(budgetdetailBean1);

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
            out = new FileOutputStream("D:\\恒逸工作文档\\财务预算系统\\预算数据导出excel.xlsx");
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


    @Scheduled(cron = "0 56 11 * * ?")
    public void exceltest() throws Exception {
        System.out.println("excel");
        File file = new File("C:\\Users\\38521\\Documents\\Tencent Files\\385213918\\FileRecv\\六家公司_预算单耗&单价.xlsx");
        // 创建文件流对象和工作簿对象
        FileInputStream in = new FileInputStream(file); // 文件流

        Workbook book = WorkbookFactory.create(in); //工作簿

        List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();

        //j: sheet,  i: 行,  k: 列

        //遍历所有的sheet
        for (int j = 0; j < book.getNumberOfSheets(); j++) {
            // 获取当前excel中sheet的下标：0开始
            Sheet sheet = book.getSheetAt(j);   // 遍历Sheet
            //遍历所有的行和列
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row!=null){
                    continue;
                }

                if (i >= 4&&row.getCell(0)!=null&&!ExcelUtil.changetostring(row.getCell(0)).equals("")) {
                    //一行数据作为一个对象插入
                    BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                    for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

                        if (68> k&&k > 11) {
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
                                mcdb.setConsumption(new BigDecimal((ExcelUtil.changetostring(cell))));
                            }else {
                                continue;
                            }
                            //单价
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price))) {
                                mcdb.setPrice(new BigDecimal(ExcelUtil.changetostring(cell_price)));
                            }else {
                                continue;
                            }
                            //单位成本
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price)) && StringUtil.isNotEmpty(ExcelUtil.changetostring(cell))) {
                                BigDecimal decimal = new BigDecimal(ExcelUtil.changetostring(cell)).multiply(new BigDecimal(ExcelUtil.changetostring(cell_price)));
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
                    budgetdetailBean.setLine(ExcelUtil.changetostring(cell_line));
                    budgetdetailBean.setSpec(ExcelUtil.changetostring(cell_spec));
                    budgetdetailBean.setYarnkind(ExcelUtil.changetostring(cell_yarnkind));

                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_aarate))) {
                        budgetdetailBean.setAarate(new BigDecimal(ExcelUtil.changetostring(cell_aarate)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_fsrate))) {
                        budgetdetailBean.setFsrate(new BigDecimal(ExcelUtil.changetostring(cell_fsrate)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_dayProduct))) {
                        budgetdetailBean.setDayProduct(new BigDecimal(ExcelUtil.changetostring(cell_dayProduct)));
                    }
                    if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_budgetTotalProduct))) {
                        budgetdetailBean.setBudgetTotalProduct(new BigDecimal(ExcelUtil.changetostring(cell_budgetTotalProduct)));
                    }
                    //遍历一行中的所有的数据添加进budgetdetailBeanList集合

                    budgetdetailBeanList.add(budgetdetailBean);
                }
            }
            for (BudgetdetailBean budgetdetailBean : budgetdetailBeanList) {
                for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                    financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
                }
                financeDataMapper.insertdetail(budgetdetailBean);
            }
        }
    }





    public void unitpricecomparetask ()  {
        ArrayList<Map<String, Object>> budgetresult = financeDataMapper.selectbudgetdata();
        ArrayList<BudgetdetailBean> Budgetdetaillist = new ArrayList<BudgetdetailBean>();
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
                    if (entry.getKey().equals("mate_pta")){
                        System.out.println(materialcostdetailsBean);
                    }
                    if (materialcostdetailsBean != null) {
                        materialcostdetailsBean.setField(entry.getKey());
                        budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                    }
                }
            }
            Budgetdetaillist.add(budgetdetailBean);
        }
           for (BudgetdetailBean budgetdetailBean:Budgetdetaillist){
            if (StringUtil.equals(budgetdetailBean.getType(),"实际")){
                for (BudgetdetailBean budgetdetailBean1:Budgetdetaillist){
                    if (StringUtil.equals(budgetdetailBean1.getType(),"预算")){




                    }
                }
            }
           }

    }
}






