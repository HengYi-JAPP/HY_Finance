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
 * @Description:生产线、规格、纱种（差异化）维度数据计算定时任务
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
    @Scheduled(cron = "00 30 05 01 * ?")
    public void productlinedatatask() {
        System.out.println("开始存放");
        //集合存放每条生产线的各个成本项数据
        ArrayList<MaterialOfLineSelectBean> materialoflinelist = new ArrayList<MaterialOfLineSelectBean>();
        //集合存放将要插入Budgetdetail表的对象
        ArrayList<BudgetdetailBean> budgetdetailBeanlist = new ArrayList<BudgetdetailBean>();
        //集合存放物料匹配关系
        ArrayList<MaterialMatchBean> materialmatchlist = new ArrayList<MaterialMatchBean>();
        //获得当前当前时间的年份与上个月份
        SapDataMonthBean sapDataMonthBean = DateUtil.getsapdatamonthbeannow();
//        sapDataMonthBean.setMonth(new BigDecimal(2));
        //清空原有表中的上个月份实际生产数据以便重新导入
        financeDataMapper.deletebudgetbymonth(sapDataMonthBean);
        //查询物料匹配关系
        materialmatchlist = financeDataMapper.selectmaterialmatch();
        //查询上个月每条生产线的各个成本项数据
        materialoflinelist = financeDataMapper.selectmaterialofline(sapDataMonthBean);
        /**
         *单耗单价计算主逻辑   将计算的结果存入BudgetDetailBean
         *
         *  再将BudgetDetailBean 放入budgetdetailBeanlist
         * */
        //遍历每条生产线的成本项数据
        for (MaterialOfLineSelectBean materialOfLineSelectBean : materialoflinelist) {
            //设置budgetdetailBeanlist中未存在该生产线、规格、纱种（差异化）维度的对象
            boolean budgetdetailBeanexist = false;
            //遍历物料匹配关系，匹配本次搜索记录的成本项

            for (MaterialMatchBean materialMatchBean : materialmatchlist) {
                //如果本条记录匹配成功，说明该生产记录是财务预算分析所需要的数据
                if ((materialMatchBean.getMaterialId() != null && StringUtil.equals(materialOfLineSelectBean.getCostMaterialId(), "00000000" + materialMatchBean.getMaterialId())&&StringUtil.equals(materialOfLineSelectBean.getState(),materialMatchBean.getState()))
                        || (materialMatchBean.getCostId() != null && StringUtil.equals(materialOfLineSelectBean.getCostId(), materialMatchBean.getCostId()) && StringUtil.equals(materialMatchBean.getState(), materialOfLineSelectBean.getState())&&StringUtil.equals(materialOfLineSelectBean.getState(),materialMatchBean.getState()))) {

                   //对budgetdetailBeanlist进行遍历，查看是否已有该生产线、规格、维度的对象
                    // 如果没有则添加，如果有则更新
                    for (BudgetdetailBean budgetdetailBean : budgetdetailBeanlist) {
                        if (StringUtil.equals(budgetdetailBean.getCompany(), materialOfLineSelectBean.getCompany())
                                && StringUtil.equals(budgetdetailBean.getProduct(), materialOfLineSelectBean.getProductName())
                                && StringUtil.equals(budgetdetailBean.getLine(), materialOfLineSelectBean.getProductLine())
                                && StringUtil.equals(budgetdetailBean.getSpec(), materialOfLineSelectBean.getProductSpecifications())
                                && StringUtil.equals(budgetdetailBean.getYarnkind(), materialOfLineSelectBean.getProductYarn())
                                && MathUtil.bigdecimalequals(budgetdetailBean.getMonth(), materialOfLineSelectBean.getMonth())
                                && MathUtil.bigdecimalequals(budgetdetailBean.getYear(), materialOfLineSelectBean.getYear())
                                && StringUtil.equals(budgetdetailBean.getType(), "实际")
                                ) {
                            boolean materialcostdetailsBeanexist = false;
                            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                                if (StringUtil.equals(materialcostdetailsBean.getMaterialName(), materialMatchBean.getMaterialName())) {
                                    materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                                    materialcostdetailsBean.setMoney(MathUtil.add(materialcostdetailsBean.getMoney(),materialOfLineSelectBean.getMoney()));
                                    materialcostdetailsBean.setKwmeng(MathUtil.add(materialcostdetailsBean.getKwmeng(),materialOfLineSelectBean.getCostQuantity()));
                                    materialcostdetailsBean.setUnitPrice(MathUtil.divide(materialcostdetailsBean.getMoney(),budgetdetailBean.getBudgetTotalProduct()));
                                    materialcostdetailsBean.setConsumption(MathUtil.divide(materialcostdetailsBean.getKwmeng(),budgetdetailBean.getBudgetTotalProduct()));
                                    materialcostdetailsBean.setPrice(MathUtil.divide(materialcostdetailsBean.getMoney(),materialcostdetailsBean.getKwmeng()));
                                    materialcostdetailsBeanexist = true;
                                    break;
                                }
                            }
                            if (!materialcostdetailsBeanexist) {
                                MaterialcostdetailsBean materialcostdetailsBean = new MaterialcostdetailsBean();
                                materialcostdetailsBean.setMaterialName(materialMatchBean.getMaterialName());
                                materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                                materialcostdetailsBean.setField(materialMatchBean.getField());
                                materialcostdetailsBean.setMoney(materialOfLineSelectBean.getMoney());
                                materialcostdetailsBean.setKwmeng(materialOfLineSelectBean.getCostQuantity());
                                materialcostdetailsBean.setUnitPrice(MathUtil.divide(materialcostdetailsBean.getMoney(),budgetdetailBean.getBudgetTotalProduct()));
                                materialcostdetailsBean.setConsumption(MathUtil.divide(materialcostdetailsBean.getKwmeng(),budgetdetailBean.getBudgetTotalProduct()));
                                materialcostdetailsBean.setPrice(MathUtil.divide(materialcostdetailsBean.getMoney(),materialcostdetailsBean.getKwmeng()));
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
                        BigDecimal totalProduct =MathUtil.divide(new BigDecimal(financeDataMapper.selectproductquantity(materialOfLineSelectBean)), new BigDecimal("1000"));
                        if (totalProduct!=null){
                            budgetdetailBean.setBudgetTotalProduct(totalProduct);
                        }
                        budgetdetailBean.setLine(materialOfLineSelectBean.getProductLine());
                        budgetdetailBean.setSpec(materialOfLineSelectBean.getProductSpecifications());
                        budgetdetailBean.setYarnkind(materialOfLineSelectBean.getProductYarn());
                        MaterialcostdetailsBean materialcostdetailsBean = new MaterialcostdetailsBean();
                        materialcostdetailsBean.setMaterialName(materialMatchBean.getMaterialName());
                        materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                        materialcostdetailsBean.setMoney(materialOfLineSelectBean.getMoney());
                        materialcostdetailsBean.setKwmeng(materialOfLineSelectBean.getCostQuantity());
                        materialcostdetailsBean.setUnitPrice(MathUtil.divide(materialcostdetailsBean.getMoney(),budgetdetailBean.getBudgetTotalProduct()));
                        materialcostdetailsBean.setConsumption(MathUtil.divide(materialcostdetailsBean.getKwmeng(),budgetdetailBean.getBudgetTotalProduct()));
                        materialcostdetailsBean.setPrice(MathUtil.divide(materialcostdetailsBean.getMoney(),materialcostdetailsBean.getKwmeng()));
                        materialcostdetailsBean.setField(materialMatchBean.getField());
                        budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                        budgetdetailBeanlist.add(budgetdetailBean);
                    }
                    break;
                }
            }
        }
        //将得到的budgetdetailBeanlist对象遍历插入到BudgetDetail表中，获得实际生产数据 生产线、规格、纱种（差异化）维度的数据
        for (BudgetdetailBean budgetdetailBean : budgetdetailBeanlist) {
            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                financeDataMapper.insertmaterialcostdetails(materialcostdetailsBean);
            }
            financeDataMapper.insertbudgetdetail(budgetdetailBean);
        }
        System.out.println("结束存放");
    }






    /**
     * @param
     * @return
     * @Transactional(rollbackFor = Exception.class)
     * 计算生产线、规格、纱种维度的单位成本并放入UnitpPriceComparetask表中
     */
    @Scheduled(cron = "00 30 06 01 * ?")
    public void unitpricecomparetask ()  {
        System.out.println("开始计算");
        //获得当前时间的年份与上月月份
        SapDataMonthBean sapDataMonthBean = DateUtil.getsapdatamonthbeannow();
//        sapDataMonthBean.setMonth(new BigDecimal(2));
        //每次更新之前清空相应数据
     financeDataMapper.deleteunitpricecomparebymonth(sapDataMonthBean);
        //查询上月的所有详情数据，包含实际与预算
        ArrayList<Map<String, Object>> budgetresult = financeDataMapper.selectbudgetdatabydate(sapDataMonthBean);
        //Budgetdetaillist 存放搜索结果转换后的实体类BudgetdetailBean  ，代表Budgetdetail中的一行数据
        ArrayList<BudgetdetailBean> Budgetdetaillist = new ArrayList<BudgetdetailBean>();

        for (Map<String, Object> singleRecord : budgetresult) {
            BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
            ArrayList<Integer>   materialdetailidlist=new ArrayList<>();
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
                    if (!(StringUtil.equals(entry.getKey(),"mate_pta")
                        ||StringUtil.equals(entry.getKey(),"mate_meg")
                        ||StringUtil.equals(entry.getKey(),"mate_poy")
                        ||StringUtil.equals(entry.getKey(),"mate_slice"))){
                        materialdetailidlist.add((Integer)entry.getValue());
                    }
                }
            }
            if (materialdetailidlist.size()>0){
            budgetdetailBean.setMaterialcostdetailsBeanArrayList( financeDataMapper.selectcostdetailbyidlist(materialdetailidlist)) ;
            }
            Budgetdetaillist.add(budgetdetailBean);
        }
           for (BudgetdetailBean budgetdetailBean:Budgetdetaillist){
            if (StringUtil.equals(budgetdetailBean.getType(),"实际")){
                UnitPriceCompareBean unitPriceCompareBean =new UnitPriceCompareBean();
                unitPriceCompareBean.setCompany(budgetdetailBean.getCompany());
                unitPriceCompareBean.setMonth(budgetdetailBean.getMonth());
                unitPriceCompareBean.setYear(budgetdetailBean.getYear());
                unitPriceCompareBean.setLine(budgetdetailBean.getLine());
                unitPriceCompareBean.setProduct(budgetdetailBean.getProduct());
                unitPriceCompareBean.setTotalProduct(budgetdetailBean.getBudgetTotalProduct());
                unitPriceCompareBean.setSpec(budgetdetailBean.getSpec());
                unitPriceCompareBean.setYarnkind(budgetdetailBean.getYarnkind());
                unitPriceCompareBean.setWorkshop(budgetdetailBean.getWorkshop());
                unitPriceCompareBean.setCheckBudgetUnitPrice(new BigDecimal(0));
                unitPriceCompareBean.setCheckProductUnitPrice(new BigDecimal(0));
                unitPriceCompareBean.setBudgetUnitPrice(new BigDecimal(0));
                BigDecimal productunitprice=new BigDecimal(0);
                for (MaterialcostdetailsBean materialcostdetailsBean:budgetdetailBean.getMaterialcostdetailsBeanArrayList()){
                    productunitprice=productunitprice.add(materialcostdetailsBean.getUnitPrice());
                }
                unitPriceCompareBean.setProductUnitPrice(productunitprice);
                for (BudgetdetailBean budgetdetailBean1:Budgetdetaillist){
                    if ( StringUtil.equals(budgetdetailBean.getCompany(), budgetdetailBean1.getCompany())
                            && StringUtil.equals(budgetdetailBean.getProduct(), budgetdetailBean1.getProduct())
                                          && MathUtil.bigdecimalequals(budgetdetailBean.getMonth(), budgetdetailBean1.getMonth())
                            && MathUtil.bigdecimalequals(budgetdetailBean.getYear(), budgetdetailBean1.getYear())
                            && StringUtil.equals(budgetdetailBean1.getType(), "预算")) {
                        if (StringUtil.equals(budgetdetailBean.getLine(), budgetdetailBean1.getLine())
                                && StringUtil.equals(budgetdetailBean.getSpec(), budgetdetailBean1.getSpec())
                                && StringUtil.equals(budgetdetailBean.getYarnkind(), budgetdetailBean1.getYarnkind())
                           ){
                            unitPriceCompareBean.setBudgetTotalProduct(budgetdetailBean1.getBudgetTotalProduct());
                            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                                for (MaterialcostdetailsBean materialcostdetailsBean1 : budgetdetailBean1.getMaterialcostdetailsBeanArrayList()) {
                                    if (StringUtil.equals(materialcostdetailsBean.getMaterialName(), materialcostdetailsBean1.getMaterialName())) {
                                        unitPriceCompareBean.setCheckProductUnitPrice(unitPriceCompareBean.getCheckProductUnitPrice().add(MathUtil.multiply(materialcostdetailsBean.getConsumption(), materialcostdetailsBean1.getPrice())));
                                        unitPriceCompareBean.setCheckBudgetUnitPrice(unitPriceCompareBean.getCheckBudgetUnitPrice().add(MathUtil.multiply(materialcostdetailsBean.getPrice(), materialcostdetailsBean1.getConsumption())));
                                        unitPriceCompareBean.setBudgetUnitPrice(unitPriceCompareBean.getBudgetUnitPrice().add(materialcostdetailsBean1.getUnitPrice()));
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
              financeDataMapper.insertunitpricecomparedata(unitPriceCompareBean);
            }
           }
        System.out.println("计算结束");
    }
}






