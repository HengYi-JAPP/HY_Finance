package com.hengyi.job;

import com.hengyi.bean.*;
import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.sapmapper.SapDataMapper;
import com.hengyi.util.DateUtil;
import com.hengyi.util.MathUtil;
import com.hengyi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    @Scheduled(cron = "0 0 10 * * ?")
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
                if ((materialMatchBean.getMaterialId() != null && StringUtil.equals(materialOfLineSelectBean.getCostMaterialId(), "00000000"+materialMatchBean.getMaterialId()))
                        || (materialMatchBean.getCostId() != null && StringUtil.equals(materialOfLineSelectBean.getCostId(), materialMatchBean.getCostId())&&StringUtil.equals(materialMatchBean.getState(),materialOfLineSelectBean.getState()))) {
                    for (BudgetdetailBean budgetdetailBean : budgetdetailBeanlist) {
                        if (StringUtil.equals(budgetdetailBean.getCompany(), materialOfLineSelectBean.getCompany())
                                && StringUtil.equals(budgetdetailBean.getProduct(), materialOfLineSelectBean.getProductName())
                                && StringUtil.equals(budgetdetailBean.getLine(), materialOfLineSelectBean.getProductLine())
                                && StringUtil.equals(budgetdetailBean.getSpec(), materialOfLineSelectBean.getProductSpecifications())
                                && StringUtil.equals(budgetdetailBean.getYarnkind(), materialOfLineSelectBean.getProductYarn())
                                && MathUtil.bigdecimalequals(budgetdetailBean.getMonth(), materialOfLineSelectBean.getMonth())
                                && StringUtil.equals(budgetdetailBean.getType(), "实际")
                                ) {
                            if (budgetdetailBean.getBudgetTotalProduct()!=null&&materialOfLineSelectBean.getOrderProductQuantity()!=null&&materialOfLineSelectBean.getOrderProductQuantity().doubleValue()>budgetdetailBean.getBudgetTotalProduct().doubleValue()){
                                budgetdetailBean.setBudgetTotalProduct(MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")));
                            }
                            boolean materialcostdetailsBeanexist = false;
                            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                                if (StringUtil.equals(materialcostdetailsBean.getMaterialName(), materialMatchBean.getMaterialName())) {
                                    materialcostdetailsBean.setPrice(MathUtil.add(materialcostdetailsBean.getPrice(), MathUtil.divide(materialOfLineSelectBean.getMoney(), materialOfLineSelectBean.getCostQuantity())));
                                    materialcostdetailsBean.setConsumption(MathUtil.add(materialcostdetailsBean.getConsumption(), MathUtil.divide(materialOfLineSelectBean.getCostQuantity(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")))));
                                    materialcostdetailsBean.setState(materialOfLineSelectBean.getState());
                                    materialcostdetailsBean.setUnitPrice(MathUtil.add(materialcostdetailsBean.getUnitPrice(), MathUtil.divide(materialOfLineSelectBean.getMoney(), MathUtil.divide(materialOfLineSelectBean.getOrderProductQuantity(), new BigDecimal("1000")))));
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
    @Scheduled(cron = "0 25 1 * * ?")
    public void companyproductdatatask() {
        ArrayList<Map<String, Object>> budgetresult = financeDataMapper.selectbudgetdata();
        ArrayList<BudgetdetailBean> Budgetdetaillist = new ArrayList<BudgetdetailBean>();
        for (Map<String,Object> singleRecord : budgetresult) {
            BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
            int num=0;

            if (singleRecord.get("id") instanceof Integer) {
                budgetdetailBean.setId(new BigDecimal((Integer) singleRecord.get("id")));
                num++;
            }
            if (singleRecord.get("year") instanceof Integer) {
                budgetdetailBean.setYear(new BigDecimal((Integer) singleRecord.get("year")));
                num++;
            }
            if (singleRecord.get("month") instanceof Integer) {
                budgetdetailBean.setMonth(new BigDecimal((Integer) singleRecord.get("month")));
                num++;
            }
            if (singleRecord.get("company") instanceof String) {
                budgetdetailBean.setCompany((String) singleRecord.get("company"));
                num++;
            }
            if (singleRecord.get("product") instanceof String) {
                budgetdetailBean.setProduct((String) singleRecord.get("product"));
                num++;
            }
            if (singleRecord.get("line") instanceof String) {
                budgetdetailBean.setLine((String) singleRecord.get("line"));
                num++;
            }
            if (singleRecord.get("spec") instanceof String) {
                budgetdetailBean.setSpec((String) singleRecord.get("spec"));
                num++;
            }
            if (singleRecord.get("yarnKind") instanceof String) {
                budgetdetailBean.setYarnkind((String) singleRecord.get("yarnKind"));
                num++;
            }
            if (singleRecord.get("workshop") instanceof String) {
                budgetdetailBean.setWorkshop((String) singleRecord.get("workshop"));
                num++;
            }
            if (singleRecord.get("AArate") instanceof Double) {
                budgetdetailBean.setAarate(new BigDecimal((Double) singleRecord.get("AArate")));
                num++;
            }
            if (singleRecord.get("FSrate") instanceof Double) {
                budgetdetailBean.setFsrate(new BigDecimal((Double) singleRecord.get("FSrate")));
                num++;
            }
            if (singleRecord.get("day_product") instanceof Double) {
                budgetdetailBean.setDayProduct(new BigDecimal((Double) singleRecord.get("day_product")));
                num++;
            }
            if (singleRecord.get("budget_total_product") instanceof Double) {
                budgetdetailBean.setBudgetTotalProduct(new BigDecimal((Double) singleRecord.get("budget_total_product")));
                num++;
            }
            if (singleRecord.get("type") instanceof String) {
                budgetdetailBean.setType((String) singleRecord.get("type"));
                num++;
            }

            int num1=0;
                for (Map.Entry<String, Object> entry : singleRecord.entrySet()) {

                    num1++;
                    if (num1<=num){
                        continue;
                    }
                    if (entry.getValue() instanceof Integer) {
                        System.out.println("jinrule");
                        MaterialcostdetailsBean materialcostdetailsBean  = financeDataMapper.selectcostdetailbyid((Integer) entry.getValue());
                        if (materialcostdetailsBean!=null){
                            System.out.println("daozhelile");
                            materialcostdetailsBean.setField(entry.getKey());
                            budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(materialcostdetailsBean);
                        }
                    }

            }
            Budgetdetaillist.add(budgetdetailBean);
            System.out.println(budgetdetailBean);
        }

    }
}






