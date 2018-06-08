package com.hengyi.service;

import com.hengyi.bean.*;
import com.hengyi.domain.DetailAddDomain;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.mapper.FinanceBudgetMapper;
import com.hengyi.util.DateUtil;
import com.hengyi.util.ExcelUtil;
import com.hengyi.util.LoggerUtil;
import com.hengyi.util.StringUtil;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuyuan
 * @create 2018-03-22 16:52
 * @description
 **/
public class FinanceBudgetServiceImpl implements FinanceBudgetService {
    @Autowired
    private FinanceBudgetMapper financeBudgetMapper;

    /***
     * 获取详细数据的方法
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetailData(ConditionVo conditionVo) {
        List<Map<String, Object>> list = financeBudgetMapper.getDetailData(conditionVo);
        List<Map<String, Object>> mapList;
        if ("实际".equals(conditionVo.getType()) || "预算".equals(conditionVo.getType())) {
            mapList = getOneTypeList(list, conditionVo);
            return mapList;
        }
        mapList = getList(list, conditionVo);
        return mapList;
    }

    /****
     * 获取成本大类（分聚酯阶段和纺丝阶段）
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, Object>> getCostItem(ConditionVo conditionVo){
        //设置条件为单位成本
//        conditionVo.setPriceOrconsumer("cost");
        //获取明细项
        List<Map<String,Object>> detailList=this.getDetailData(conditionVo);
        //用于存储结果
        List<Map<String,Object>> resultList=new ArrayList<>();
        //对明细项进行累加合计
        for (Map<String,Object> detail: detailList) {
            Map<String, Object> hashMap = new LinkedHashMap<String, Object>();
            //聚酯辅料
            double polyAuxCount = 0;
            //聚酯工资
            double polySalaryCount = 0;
            //聚酯折旧
            double polyDepCount=0;
            //聚酯耗电
            double polyHdlCount=0;
            //聚酯耗水
            double polyHslCount=0;
            //聚酯机配件
            double polyMacCount=0;
            //聚酯燃料
            double polyFuelCount=0;
            //聚酯其他
            double polyElseCount=0;
            //聚酯制造费用合计
            double polyCount=0;
            //纺丝辅料
            double spinAuxCount=0;
            //包装费
            double spinPackCount=0;
            //纸管
            double spinPaperCount=0;
            //聚酯熔体
//        double spinMeltCount=0;
            //纺丝工资
            double spinSalaryCount=0;
            //纺丝折旧
            double spinDepCount=0;
            //纺丝耗水
            double spinWaterCount=0;
            //纺丝耗电
            double spinEelectCount=0;
            //纺丝机配件
            double spinMacCount=0;
            //纺丝燃料
            double spinFuelCount=0;
            //纺丝其他
            double spinElseCount=0;
            //纺丝制造费用合计
            double spinCount=0;
            for (String key:detail.keySet()) {
                if ("id".equals(key)||"id1".equals(key)){
                }else if ("type".equals(key)||"type1".equals(key)||"company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) ||"company1".equals(key) ||
                        "month1".equals(key) || "year1".equals(key) ||
                        "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                        || "spec1".equals(key) || "yarnKind1".equals(key)||"budget_total_product".equals(key)||"budget_total_product1".equals(key)){
                    hashMap.put(key,detail.get(key));
                }else if (key.contains("poly_aux")){
                    polyAuxCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_salary")){
                    polySalaryCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_dep")){
                    polyDepCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_hdl")){
                    polyHdlCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_hsl")){
                    polyHslCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_mac")){
                    polyMacCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_fuel")){
                    polyFuelCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("poly_else")){
                    polyElseCount+=Double.parseDouble(detail.get(key).toString());
                    polyCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_aux")){
                    spinAuxCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_pack")){
                    spinPackCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_paper")){
                    spinPaperCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_salary")){
                    spinSalaryCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_dep")){
                    spinDepCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_water")){
                    spinWaterCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_elect")){
                    spinEelectCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_mac")){
                    spinMacCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_fuel")){
                    spinFuelCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("spin_else")){
                    spinElseCount+=Double.parseDouble(detail.get(key).toString());
                    spinCount+=Double.parseDouble(detail.get(key).toString());
                }
            }
            hashMap.put("poly_aux",changeIntoBigdecimal(polyAuxCount));
            hashMap.put("poly_salary",changeIntoBigdecimal(polySalaryCount));
            hashMap.put("poly_dep",changeIntoBigdecimal(polyDepCount));
            hashMap.put("poly_hdl",changeIntoBigdecimal(polyHdlCount));
            hashMap.put("poly_hsl",changeIntoBigdecimal(polyHslCount));
            hashMap.put("poly_mac",changeIntoBigdecimal(polyMacCount));
            hashMap.put("poly_fuel",changeIntoBigdecimal(polyFuelCount));
            hashMap.put("poly_else",changeIntoBigdecimal(polyElseCount));
            hashMap.put("poly",changeIntoBigdecimal(polyCount));
            hashMap.put("spin_aux",changeIntoBigdecimal(spinAuxCount));
            hashMap.put("spin_pack",changeIntoBigdecimal(spinPackCount));
            hashMap.put("spin_paper",changeIntoBigdecimal(spinPaperCount));
            hashMap.put("spin_salary",changeIntoBigdecimal(spinSalaryCount));
            hashMap.put("spin_dep",changeIntoBigdecimal(spinDepCount));
            hashMap.put("spin_water",changeIntoBigdecimal(spinWaterCount));
            hashMap.put("spin_elect",changeIntoBigdecimal(spinEelectCount));
            hashMap.put("spin_mac",changeIntoBigdecimal(spinMacCount));
            hashMap.put("spin_fuel",changeIntoBigdecimal(spinFuelCount));
            hashMap.put("spin_else",changeIntoBigdecimal(spinElseCount));
            hashMap.put("spin",changeIntoBigdecimal(spinCount));
            resultList.add(hashMap);
        }
        return resultList;
    }

    /****
     * 获取成本大类(不分阶段)
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, Object>> getSumCostItem(ConditionVo conditionVo) {
        //设置条件为单位成本
//        conditionVo.setPriceOrconsumer("cost");
        //获取明细项
        List<Map<String,Object>> detailList=this.getDetailData(conditionVo);
        //用于存储结果
        List<Map<String,Object>> resultList=new ArrayList<>();
        //对明细项进行累加合计
        for (Map<String,Object> detail: detailList) {
            Map<String, Object> hashMap = new LinkedHashMap<String, Object>();
            //辅料
            double AuxCount = 0;
            //包装费
            double PackCount=0;
            //纸管
            double PaperCount=0;
            //工资
            double SalaryCount = 0;
            //折旧
            double DepCount=0;
            //耗电
            double HdlCount=0;
            //耗水
            double HslCount=0;
            //机配件
            double MacCount=0;
            //燃料
            double FuelCount=0;
            //其他
            double ElseCount=0;
            //熔体
//        double spinMeltCount=0;
            //制造费用合计
            double Count=0;
            for (String key:detail.keySet()) {
                if ("id".equals(key)||"id1".equals(key)){
                }else if ("type".equals(key)||"type1".equals(key)||"company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) ||"company1".equals(key) ||
                        "month1".equals(key) || "year1".equals(key) ||
                        "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                        || "spec1".equals(key) || "yarnKind1".equals(key)||"budget_total_product".equals(key)||"budget_total_product1".equals(key)){
                    hashMap.put(key,detail.get(key));
                }else if (key.contains("aux")){
                    AuxCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("pack")){
                    PackCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("paper")){
                    PaperCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("salary")){
                    SalaryCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("dep")){
                    DepCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("hdl")||key.contains("elect")){
                    HdlCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("hsl")||key.contains("water")){
                    HslCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("mac")){
                    MacCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("fuel")){
                    FuelCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("else")){
                    ElseCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }
            }
            hashMap.put("aux",changeIntoBigdecimal(AuxCount));
            hashMap.put("pack",changeIntoBigdecimal(PackCount));
            hashMap.put("paper",changeIntoBigdecimal(PaperCount));
            hashMap.put("salary",changeIntoBigdecimal(SalaryCount));
            hashMap.put("dep",changeIntoBigdecimal(DepCount));
            hashMap.put("hsl",changeIntoBigdecimal(HslCount));
            hashMap.put("hdl",changeIntoBigdecimal(HdlCount));
            hashMap.put("mac",changeIntoBigdecimal(MacCount));
            hashMap.put("fuel",changeIntoBigdecimal(FuelCount));
            hashMap.put("else",changeIntoBigdecimal(ElseCount));
            hashMap.put("count",changeIntoBigdecimal(Count));
            resultList.add(hashMap);
        }
        return resultList;
    }

    /***
     * 获取详情合计的考核维度的均值
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, Object>> getSumDetail(ConditionVo conditionVo) {
        //将分页去除查询出所有数据
        conditionVo.setOffset(null);
        conditionVo.setLimit(null);
        //存储考核维度实际成本均值
        HashMap<String,Object> fact=new LinkedHashMap<>();
        //存储考核维度预算成本均值
        HashMap<String,Object> budget=new LinkedHashMap<>();
        //存储考核维度差异均值
        HashMap<String,Object> difference=new LinkedHashMap<>();
        //存放均值结果（共三个map）
        List<Map<String,Object>> resultList=new ArrayList<Map<String, Object>>();
        //获取所有详情记录
        List<Map<String,Object>> list= financeBudgetMapper.getDetailData(conditionVo);
        //将详情记录id转化为相应值并按实际，预算，差异的顺序查找出来
        List<Map<String,Object>> mapList=getList(list,conditionVo);
        double sumFactProduct=0;
        String [] array=new String[]{"company","month","year","product","workshop","line","spec","yarnKind","AArate","FSrate","day_product"};
        String [] array2=new String[]{conditionVo.getCompany(),conditionVo.getStartMonth().toString(),conditionVo.getEndMonth().toString(),conditionVo.getProduct(),conditionVo.getWorkshop(),conditionVo.getProductLine(),conditionVo.getSpec(),null,null,null,null};
        fact.put("type","实际");
        budget.put("type","预算");
        difference.put("type","差异");
        for (int i = 0; i < array.length; i++) {
            fact.put(array[i],array2[i]);
            budget.put(array[i],array2[i]);
            difference.put(array[i],array2[i]);
        }
        //遍历所有字段（共70个字段，循环70次）
        for (String key:mapList.get(0).keySet()) {
//            BigDecimal sumFact= new BigDecimal(0);
//            BigDecimal sumBudget=new BigDecimal(0);
            double sumFact=0;
            double sumBudget=0;
            if ("id".equals(key)||"id1".equals(key)||"type".equals(key)||"type1".equals(key)||"company".equals(key) || "month".equals(key) || "year".equals(key) ||
                    "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                    || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) ||
                    "FSrate".equals(key) || "day_product".equals(key)||"company1".equals(key) ||
                    "month1".equals(key) || "year1".equals(key) ||
                    "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                    || "spec1".equals(key) || "yarnKind1".equals(key) || "AArate1".equals(key) ||
                    "FSrate1".equals(key) || "day_product1".equals(key)) {
                continue;
            }else {
                for (int i = 0; i < mapList.size() ; i++) {
                    if ((i+1)%3 ==0){
                    }else {
                        //对实际进行求和
                        if (i%3 == 0){
                            sumFact+=Double.parseDouble(mapList.get(i).get(key).toString())*Double.parseDouble((mapList.get(i).get("budget_total_product").toString()));
//                            sumFact=sumFact.add(new BigDecimal(mapList.get(i).get(key).toString()).multiply(new BigDecimal(mapList.get(i).get("budget_total_product").toString())));
                            if ("budget_total_product".equals(key)){
                                // 累计实际总产量
                                sumFactProduct=sumFactProduct+Double.parseDouble(mapList.get(i).get("budget_total_product").toString());
                            }
                        }else{
                            //对预算进行求和
                            if ("budget_total_product".equals(key)) {
                            }else {
                                sumBudget=sumBudget+Double.parseDouble(mapList.get(i).get(key+"1").toString())*Double.parseDouble(mapList.get(i-1).get("budget_total_product").toString());
                            }
                        }
                    }
                }
                fact.put(key,sumFactProduct);
                budget.put(key,sumFactProduct);
                budget.put(key,sumFactProduct);
                if (sumFactProduct==0){
                    fact.put(key,0);
                    budget.put(key,0);
                    difference.put(key,0);
                }else {
                    fact.put(key,new BigDecimal(sumFact/sumFactProduct).setScale(5,BigDecimal.ROUND_HALF_UP));
                    budget.put(key,new BigDecimal(sumBudget/sumFactProduct).setScale(5,BigDecimal.ROUND_HALF_UP));
                    difference.put(key,new BigDecimal(sumFact/sumFactProduct-sumBudget/sumFactProduct).setScale(5,BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        resultList.add(fact);
        resultList.add(budget);
        resultList.add(difference);
        return resultList;
    }

    @Override
    public List<Map<String, Object>> getSumOverview(ConditionVo conditionVo) {
        //将分页去除查询出所有数据
        conditionVo.setOffset(null);
        conditionVo.setLimit(null);
        //存储考核维度实际成本均值
        HashMap<String,Object> fact=new LinkedHashMap<>();
        //存储考核维度预算成本均值
        HashMap<String,Object> budget=new LinkedHashMap<>();
        //存储考核维度差异均值
        HashMap<String,Object> difference=new LinkedHashMap<>();
        //存放均值结果（共三个map）
        List<Map<String,Object>> resultList=new ArrayList<Map<String, Object>>();
        //存放计算结果
        List<Map<String,Object>> list= null;
        double sumFactProduct=0;
        String [] array=new String[]{"company","month","year","product","workshop","line","spec","yarnKind"};
        String [] array2=new String[]{conditionVo.getCompany(),conditionVo.getStartMonth().toString(),conditionVo.getEndMonth().toString(),conditionVo.getProduct(),conditionVo.getWorkshop(),conditionVo.getProductLine(),conditionVo.getSpec(),null,null,null,null};
        fact.put("type","实际");
        budget.put("type","预算");
        difference.put("type","差异");
        for (int i = 0; i < array.length; i++) {
            fact.put(array[i],array2[i]);
            budget.put(array[i],array2[i]);
            difference.put(array[i],array2[i]);
        }
        if ("stage".equals(conditionVo.getStageType())) {
            list = this.getCostItem(conditionVo);
        }else if ("noneStage".equals(conditionVo.getStageType())) {
            list = this.getSumCostItem(conditionVo);
        }
            for (String key : list.get(0).keySet()) {
                double sumFact = 0;
                double sumBudget = 0;
                if ("id".equals(key) || "id1".equals(key) || "type".equals(key) || "type1".equals(key) || "company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) ||
                        "FSrate".equals(key) || "day_product".equals(key) || "company1".equals(key) ||
                        "month1".equals(key) || "year1".equals(key) ||
                        "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                        || "spec1".equals(key) || "yarnKind1".equals(key) || "AArate1".equals(key) ||
                        "FSrate1".equals(key) || "day_product1".equals(key)) {
                    continue;
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if ((i + 1) % 3 == 0) {
                        } else {
                            //对实际进行求和
                            if (i%3 == 0){
                                sumFact+=Double.parseDouble(list.get(i).get(key).toString())*Double.parseDouble((list.get(i).get("budget_total_product").toString()));
//                            sumFact=sumFact.add(new BigDecimal(mapList.get(i).get(key).toString()).multiply(new BigDecimal(mapList.get(i).get("budget_total_product").toString())));
                                if ("budget_total_product".equals(key)){
                                    // 累计实际总产量
                                    sumFactProduct=sumFactProduct+Double.parseDouble(list.get(i).get("budget_total_product").toString());
                                }
                            }else{
                                //对预算进行求和
                                if("budget_total_product".equals(key)){
                                }else {
                                    sumBudget=sumBudget+Double.parseDouble(list.get(i).get(key).toString())*Double.parseDouble(list.get(i-1).get("budget_total_product").toString());
                                }
                            }
                        }
                    }
                    fact.put(key,sumFactProduct);
                    budget.put(key,sumFactProduct);
                    budget.put(key,sumFactProduct);
                    if (sumFactProduct==0){
                        fact.put(key,0);
                        budget.put(key,0);
                        difference.put(key,0);
                    }else {
                        fact.put(key,new BigDecimal(sumFact/sumFactProduct).setScale(5,BigDecimal.ROUND_HALF_UP));
                        budget.put(key,new BigDecimal(sumBudget/sumFactProduct).setScale(5,BigDecimal.ROUND_HALF_UP));
                        difference.put(key,new BigDecimal(sumFact/sumFactProduct-sumBudget/sumFactProduct).setScale(5,BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        resultList.add(fact);
        resultList.add(budget);
        resultList.add(difference);
        return resultList;
    }

//    /***
//     * 获取预算或实际总和均值
//     * @param mapList
//     * @param sumFactProduct
//     * @param resultMap
//     * @return
//     */
//    private HashMap<String,Object> getSum(List<Map<String,Object>> mapList,BigDecimal sumFactProduct,HashMap<String,Object> resultMap){
//        //剔除map非计算字段
//        String [] array=new String[]{"id","type","company","month","year","product","workshop","line","spec","yarnKind","AArate","FSrate","day_product","budget_total_product"};
//        for (int i = 0; i < array.length; i++) {
//            resultMap.put(array[i],mapList.get(0).get(array[i]));
//            mapList.get(0).remove(array[i]);
//        }
//        //对所有需要计算的字段进行遍历相加
//        for (String key: mapList.get(0).keySet()) {
//            //初始化一个合计标记
//            BigDecimal sum=new BigDecimal(0);
//            //对每个需要计算字段的值进行遍历相加
//            for (Map map:mapList) {
//                if (!StringUtil.isEmpty(map.get(key))){
//                    sum=sum.add(new BigDecimal(map.get(key).toString()).multiply(new BigDecimal(map.get("budget_total_product").toString())));
//                }else{
//                    continue;
//                }
//            }
//            resultMap.put(key,sum.subtract(sumFactProduct));
//        }
//        return resultMap;
//    }

    /***
     * 获取字典表数据
     * @return
     */
    @Override
    public List<DictionaryDomain> getDictionary() {
        return financeBudgetMapper.getDictionary();
    }

    /***
     * 获取车间列表
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, String>> getWorkshop(ConditionVo conditionVo) {
        return financeBudgetMapper.getWorkshop(conditionVo);
    }

    /***
     * 获取生产线列表
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, String>> getLine(ConditionVo conditionVo) {
        return financeBudgetMapper.getLine(conditionVo);
    }

    /***
     * 获取规格列表
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, String>> getSpec(ConditionVo conditionVo) {
        return financeBudgetMapper.getSpec(conditionVo);
    }

    /***
     * 获取详情表总记录数
     * @param conditionVo
     * @return
     */
    @Override
    public Long getTotalCount(ConditionVo conditionVo) {
        return financeBudgetMapper.getTotalCount(conditionVo);
    }

    /***
     * 获取结果数据
     * @param conditionVo
     * @return
     */
    @Override
    public List<ResultDomain> getResult(ConditionVo conditionVo) {
        List<ResultDomain> list = financeBudgetMapper.getResult(conditionVo);
        for (ResultDomain resultDomain : list) {
            resultDomain.setPriceEffect(resultDomain.getProductUnitPrice().subtract(resultDomain.getCheckProductUnitPrice()));
            resultDomain.setConsumerEffect(resultDomain.getCheckProductUnitPrice().subtract(resultDomain.getCheckBudgetUnitPrice()));
            resultDomain.setStructureEffect(resultDomain.getCheckBudgetUnitPrice().subtract(resultDomain.getBudgetUnitPrice()));
            resultDomain.setTotalDifference(resultDomain.getProductUnitPrice().subtract(resultDomain.getBudgetUnitPrice()));
        }
        return list;
    }

    /***
     * 获取结果数据记录数
     * @param conditionVo
     * @return
     */
    @Override
    public Long getResultCount(ConditionVo conditionVo) {
        return financeBudgetMapper.getResultCount(conditionVo);
    }

    /***
     * 获取公司维度的数据
     * @param conditionVo
     * @return
     */
    @Override
    public List<AllCompanyResultVo> getAllCompanyData(ConditionVo conditionVo) {
        List<AllCompanyResultVo> list = financeBudgetMapper.getAllCompanyData(conditionVo);
        for (AllCompanyResultVo vo : list) {
            vo.setPriceEffect(vo.getProductUnitPrice().subtract(vo.getCheckProductUnitPrice()));
            vo.setConsumerEffect(vo.getCheckProductUnitPrice().subtract(vo.getCheckBudgetUnitPrice()));
            vo.setStructureEffect(vo.getCheckBudgetUnitPrice().subtract(vo.getBudgetUnitPrice()));
            vo.setTotalDifference(vo.getProductUnitPrice().subtract(vo.getBudgetUnitPrice()));
        }
        return list;
    }

    /***
     * 获取新增规格的总记录数
     * @param conditionVo
     * @return
     */
    @Override
    public Long getNewlyIncreasedCount(ConditionVo conditionVo) {
        return financeBudgetMapper.getNewlyIncreasedCount(conditionVo);
    }

    /***
     * 获取新增规格的列表
     * @param conditionVo
     * @return
     */
    @Override
    public List<DetailAddDomain> getNewlyIncreased(ConditionVo conditionVo) {
        List<DetailAddDomain> list=financeBudgetMapper.getNewlyIncreasd(conditionVo);
        return list;
    }

    /***
     * 获取公司维度的总记录数
     * @param conditionVo
     * @return
     */
    @Override
    public Long getAllCompanyDataCount(ConditionVo conditionVo) {
        return financeBudgetMapper.getAllCompanyDataCount(conditionVo);
    }

    /***
     * 将上传的Excel数据插入数据库中
     * @param file
     * @throws IOException
     * @throws InvalidFormatException
     */
    @Override
    public void importBudgetData(File file) throws IOException, InvalidFormatException {
        System.out.println("开始了");
//        File file = new File("C:\\Users\\38521\\Documents\\Tencent Files\\385213918\\FileRecv\\六家公司_预算单耗&单价（修改2018.04.01凌晨）.xlsx");
        // 创建文件流对象和工作簿对象
        FileInputStream in = new FileInputStream(file); // 文件流
        Workbook book = WorkbookFactory.create(in); //工作簿
        List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();
        //将生产线匹配关系放入集合
        ArrayList<ProductMatchBean> productmatchlist = new ArrayList<>();
        productmatchlist = financeBudgetMapper.selectproductmatch();
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
                if (i >= 4&&row.getCell(0)!=null&&!ExcelUtil.changetostring(row.getCell(0),j,i,0).equals("")) {
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
                            ArrayList<String> fields=financeBudgetMapper.selectfieldbymaterialname(mcdb.getMaterialName());
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
                        if (StringUtil.equals(productMatchBean.getProductLine(),budgetdetailBean.getLine())){
                            lines=productMatchBean.getProductMatch().split("/");
                            break;
                        }
                    }

                    for (int k =0;k<lines.length;k++){
                        budgetdetailBean.setLine(lines[k]);
                        ArrayList<String>  result= financeBudgetMapper.selectbudgetdatabybean(budgetdetailBean);
                        if (result.isEmpty()){
                            for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                                financeBudgetMapper.insertmaterialcostdetails(materialcostdetailsBean);
                            }
                            financeBudgetMapper.insertdetail(budgetdetailBean);
                            System.out.println(budgetdetailBeanList.size());
                            budgetdetailBeanList.add(budgetdetailBean);
                        }
                    }
                }
            }
        }
    }

    /***
     * 将id转化对应的值
     * @param map
     * @param key
     * @param conditionVo
     * @return
     */
    private Map<String, Object> getValue(Map<String, Object> resultMap, String key, ConditionVo conditionVo, Map<String, Object> map) {
        if (StringUtil.isEmpty(map.get(key))) {
            resultMap.put(key, 0);
        } else {
            MaterialcostdetailsBean materialcostdetailsBean = financeBudgetMapper.getCostDetail(Integer.parseInt(map.get(key).toString()));
            if ("price".equals(conditionVo.getPriceOrconsumer())) {
                resultMap.put(key, materialcostdetailsBean.getPrice());
            } else if ("consumer".equals(conditionVo.getPriceOrconsumer())) {
                resultMap.put(key, materialcostdetailsBean.getConsumption().setScale(3, BigDecimal.ROUND_HALF_UP));
            } else if ("cost".equals(conditionVo.getPriceOrconsumer())) {
                resultMap.put(key, materialcostdetailsBean.getUnitPrice());
            } else if ("checkUnitPrice".equals(conditionVo.getPriceOrconsumer())) {
                resultMap.put(key,materialcostdetailsBean.getCheckProductUnitPrice());
            }
        }
        return resultMap;
    }

    /***
     * 获取实际和预算数据的列表
     * @param list
     * @param conditionVo
     * @return
     */
    private List<Map<String, Object>> getList(List<Map<String, Object>> list, ConditionVo conditionVo) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            //用于存放实际的详情数据
            Map<String, Object> hashMapFact = new LinkedHashMap<String, Object>();
            //用于存放预算的详情数据
            Map<String, Object> hashMapBudget = new LinkedHashMap<String, Object>();
            //用于存放实际与预算的差异（实际-预算）
            Map<String, Object> hashMapDifference = new LinkedHashMap<String, Object>();
            for (String key : map.keySet()) {
                if ("id".equals(key) || "id1".equals(key)) {
                } else if ("type".equals(key)) {
                    hashMapFact.put(key, "实际");
                    hashMapDifference.put(key, "差异");
                } else if ("type1".equals(key)) {
                    hashMapBudget.put(key, "预算");
                } else if ("budget_total_product".equals(key)) {
                    if (StringUtil.isEmpty(map.get(key))){
                        hashMapFact.put(key,0);
                    }else {
                        hashMapFact.put(key,map.get(key));
                    }
                } else if ("budget_total_product1".equals(key)) {
                    if (StringUtil.isEmpty(map.get(key))){
                        hashMapBudget.put(key,0);
                    }else {
                        hashMapBudget.put(key,map.get(key));
                    }
                    StringBuilder sb=new StringBuilder(key);
//                    hashMapDifference.put(key,null);
                    hashMapDifference.put(key,new BigDecimal(hashMapFact.get(sb.deleteCharAt(sb.length()-1).toString()).toString()).subtract(new BigDecimal(hashMapBudget.get(key).toString())));
                } else if ("company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) ||
                        "FSrate".equals(key) || "day_product".equals(key)) {
                    hashMapFact.put(key, map.get(key));
                    hashMapDifference.put(key, null);
                } else if ("company1".equals(key) || "month1".equals(key) || "year1".equals(key) ||
                        "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                        || "spec1".equals(key) || "yarnKind1".equals(key) || "AArate1".equals(key) ||
                        "FSrate1".equals(key) || "day_product1".equals(key)) {
                    hashMapBudget.put(key, map.get(key));
                } else {
                    String key1 = key;
                    if ("1".equals(key.substring(key.length() - 1, key.length()))) {
                        hashMapBudget = getValue(hashMapBudget, key1, conditionVo, map);
                        StringBuilder sb = new StringBuilder(key);
//                        System.out.println(hashMapBudget.get(key));
                        hashMapDifference.put(key, new BigDecimal(hashMapFact.get(sb.deleteCharAt(sb.length() - 1).toString()).toString()).subtract(new BigDecimal(hashMapBudget.get(key).toString())));
                    } else {
                        hashMapFact = getValue(hashMapFact, key, conditionVo, map);
                    }
                }
            }
            mapList.add(hashMapFact);
            mapList.add(hashMapBudget);
            mapList.add(hashMapDifference);
        }
        return mapList;
    }

    /***
     * 当传入一个类型的值的时候所使用的查询方法
     * @param list
     * @param conditionVo
     * @return
     */
    private List<Map<String, Object>> getOneTypeList(List<Map<String, Object>> list, ConditionVo conditionVo) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            Map<String, Object> hashMap = new LinkedHashMap<String, Object>();
            for (String key : map.keySet()) {
                if ("id".equals(key)) {
                } else if ("type".equals(key) || "company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) || "FSrate".equals(key) || "day_product".equals(key) || "budget_total_product".equals(key)) {
                    hashMap.put(key, map.get(key));
                } else {
                    hashMap = getValue(hashMap, key, conditionVo, map);
                }
            }
            mapList.add(hashMap);
        }
        return mapList;
    }

    /***
     * 将double类型转化为6位的Bigdecimal
     * @param value
     * @return
     */
    private BigDecimal changeIntoBigdecimal(double value){
        return new BigDecimal(value).setScale(6,BigDecimal.ROUND_HALF_UP);
    }

    /***
     * 根据条件导出详情Excel数据
     * @param conditionVo
     * @throws FileNotFoundException
     */
    @Override
    public void exportExcel(ConditionVo conditionVo) throws FileNotFoundException {
        ArrayList<LinkedHashMap<String, Object>> budgetresult=financeBudgetMapper.getBudgetDetail(conditionVo);
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
                            MaterialcostdetailsBean materialcostdetailsBean = financeBudgetMapper.selectBudgetDetailById(Integer.parseInt(map.get(key).toString()));
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

    /***
     * 根据查询条件导出成本项大类Excel，并返回存放路径
     * @param conditionVo
     * @return
     */
    @Override
    public String exportOverviewExcel(ConditionVo conditionVo) throws IOException {
        //初始化路径
        String filePath= "";
        //初始化
        List<Map<String,Object>> list =null;
        //初始化阶段
        String stage="";
        //初始化
        String demension="";
        if ("stage".equals(conditionVo.getStageType())){
            list=getCostItem(conditionVo);
            stage="分阶段";
        }else if("noneStage".equals(conditionVo.getStageType())){
            list=getSumCostItem(conditionVo);
            stage="不分阶段";
        }

        if ("dimension".equals(conditionVo.getDimension())){
            demension="考核维度";
        }else if ("noneDimension".equals(conditionVo.getDimension())){
            demension="非考核维度";
        }
        filePath = "C:\\Users\\Administrator\\Desktop\\finance\\exportExcel\\"+stage+demension+"导出成本项大类.xlsx";
        File file=new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream out=new FileOutputStream(new File(filePath));
        SXSSFWorkbook book=new SXSSFWorkbook(1000);
        System.out.println("开始导出");
        try {
            //添加表头
            int num2 = 0;
            //表头的列标
            int i=0;
            Row row0=book.createSheet().createRow(num2);
            for (String key:list.get(0).keySet()) {
                if ("id".equals(key)) {
                } else if ("type".equals(key)||"type1".equals(key)||"company".equals(key) || "month".equals(key) || "year".equals(key) ||
                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                        || "spec".equals(key) || "yarnKind".equals(key) ||"company1".equals(key) ||
                        "month1".equals(key) || "year1".equals(key) ||
                        "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                        || "spec1".equals(key) || "yarnKind1".equals(key)||"budget_total_product".equals(key)||"budget_total_product1".equals(key)) {
                    Cell cell=row0.createCell(i++);
                    cell.setCellValue(key);
                }else {
                    Cell cell0=row0.createCell(i++);
                    cell0.setCellValue(key);
                }

            }
            //开始添加数据
            int num3=1;
            for (Map<String,Object> map : list) {
                Row row=book.getSheetAt(0).createRow(num3++);
                //数据行的列标
                int j =0;
                for (String key : map.keySet()) {
                    if ("id".equals(key)) {
                    } else if ("type".equals(key)||"type1".equals(key)||"company".equals(key) || "month".equals(key) || "year".equals(key) ||
                            "product".equals(key) || "workshop".equals(key) || "line".equals(key)
                            || "spec".equals(key) || "yarnKind".equals(key) ||"company1".equals(key) ||
                            "month1".equals(key) || "year1".equals(key) ||
                            "product1".equals(key) || "workshop1".equals(key) || "line1".equals(key)
                            || "spec1".equals(key) || "yarnKind1".equals(key)||"budget_total_product".equals(key)||"budget_total_product1".equals(key)) {
                        Cell cell=row.createCell(j++);
                        if (!StringUtil.isEmpty(map.get(key))){
                            cell.setCellValue(map.get(key).toString());
                        }else {
                            cell.setCellValue("");
                        }
                    } else {
                        if (!StringUtil.isEmpty(map.get(key))){
                            Cell cell=row.createCell(j++);
                            cell.setCellValue(map.get(key).toString());
                        }else {
                            Cell cell = row.createCell(j++);
                            cell.setCellValue("0");
                        }
                    }
                }
            }
            book.write(out);
            Date day=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("结束时间："+df.format(day));
            out.close();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /***
     * 根据条件导出公司维度的数据
     * @param conditionVo
     * @return
     */
    @Override
    public String exportAllCompanyExcel(ConditionVo conditionVo) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //初始化一个文件路径对象
        String filePath="";
        List<AllCompanyResultVo> list=getAllCompanyData(conditionVo);
        filePath = "C:\\Users\\Administrator\\Desktop\\finance\\exportExcel\\"+"导出公司维度预算对比结果.xlsx";
        File file=new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream out=new FileOutputStream(new File(filePath));
        SXSSFWorkbook book=new SXSSFWorkbook(1000);
        //添加表头
        int num2 = 0;
        //表头的列标
        int i=0;
        Row row0=book.createSheet().createRow(num2);
        //获取公司维度结果对象的所有属性值
        Field[] fields= AllCompanyResultVo.class.getDeclaredFields();
        //遍历公司维度结果对象的所有属性值,并把它放到单元格中
        for ( i=0;i<fields.length;i++){
            Cell cell=row0.createCell(i);
            cell.setCellValue(fields[i].getName());
        }
        //遍历公司维度的结果,开始添加结果数据
        int num3=0;
        for (AllCompanyResultVo allCompanyResultVo:list) {
            Row row=book.getSheetAt(0).createRow(num3++);
            for (i=0;i<fields.length;i++){
                Cell cell=row.createCell(i);
                String firstLetter = fields[i].getName().substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fields[i].getName().substring(0, 1).substring(1);
                Method method = allCompanyResultVo.getClass().getMethod(getter, new Class[] {});
                Object value = method.invoke(allCompanyResultVo, new Object[] {});
                cell.setCellValue(value.toString());
            }
        }
        book.write(out);
        return filePath;
    }

    /***
     * 根据条件先导出一份单个规格的Excel文件，以供前端调用下载
     * @param conditionVo
     * @return
     * @throws IOException
     */
    @Override
    public String exportResultExcel(ConditionVo conditionVo) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //初始化一个文件路径对象
        String filePath="";
        List<ResultDomain> list=getResult(conditionVo);
        filePath = "C:\\Users\\Administrator\\Desktop\\finance\\exportExcel\\"+"导出公司维度预算对比结果.xlsx";
        File file=new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream out=new FileOutputStream(new File(filePath));
        SXSSFWorkbook book=new SXSSFWorkbook(1000);
        //添加表头
        int num2 = 0;
        //表头的列标
        int i=0;
        Row row0=book.createSheet().createRow(num2);
        //获取规格维度结果对象的所有属性值
        Field[] fields= ResultDomain.class.getDeclaredFields();
        //遍历规格维度结果对象的所有属性值,并把它放到单元格中
        for ( i=0;i<fields.length;i++){
            Cell cell=row0.createCell(i);
            cell.setCellValue(fields[i].getName());
        }
        //遍历规格维度的结果,开始添加结果数据
        int num3=0;
        for (ResultDomain resultDomain:list) {
            Row row=book.getSheetAt(0).createRow(num3++);
            for (i=0;i<fields.length;i++){
                Cell cell=row.createCell(i);
                String firstLetter = fields[i].getName().substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fields[i].getName().substring(0, 1).substring(1);
                Method method = resultDomain.getClass().getMethod(getter, new Class[] {});
                Object value = method.invoke(resultDomain, new Object[] {});
                cell.setCellValue(value.toString());
            }
        }
        book.write(out);
        return filePath;
    }

    /***
     * 将预算数据插入到数据库中
     */
    @Override
    public void insertBudgetData() {
        try {
            //删除当月的预算数据
            SapDataMonthBean sapDataMonthBean = DateUtil.getsapdatamonthbeannow();
            //删除当前月份的预算详情数据的预算数据对应的值（即MaterialCostDetails表中的值）
            financeBudgetMapper.deleteBudgetDetailValue(sapDataMonthBean);
            //删除当前月份的预算详情数据的预算数据
            financeBudgetMapper.deleteBudgetDetail(sapDataMonthBean);
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LoggerUtil.info("开始导入预算数据到budgetdetail表：" + df.format(day));
            String path = "C:\\Users\\hi60\\Desktop\\finance\\importExcel";
            File directory = new File(path);
            String[] files = directory.list();
            String file = "";
            if (files.length > 0) {
                for (String myFile : files) {
                    file = myFile;
                }
            }
            // 创建文件流对象和工作簿对象
            FileInputStream in = new FileInputStream(path + "//" + file); // 文件流
            Workbook book = WorkbookFactory.create(in); //工作簿
            System.out.println(book);
            List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();
            //将生产线匹配关系放入集合
            ArrayList<ProductMatchBean> productmatchlist = new ArrayList<>();
            productmatchlist = financeBudgetMapper.selectproductmatch();
            //获取字段
            List<FieldBean> fields = new ArrayList<FieldBean>();
            fields = financeBudgetMapper.selectField();
            //根据月份获取预算数据
            List<BudgetdetailBean> budgetDataByMonths = new ArrayList<BudgetdetailBean>();
            budgetDataByMonths = financeBudgetMapper.selectBudgetDataByYear(sapDataMonthBean);
            //j: sheet,  i: 行,  k: 列
            //遍历所有的sheet
            for (int j = 0; j < book.getNumberOfSheets(); j++) {
                // 获取当前excel中sheet的下标：0开始
                Sheet sheet = book.getSheetAt(j);   // 遍历Sheet
                //遍历所有的行和列
                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    if (i >= 4 && row.getCell(0) != null && !ExcelUtil.changetostring(row.getCell(0), j, i, 0).equals("")) {
                        //一行数据作为一个对象插入
                        BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                        budgetdetailBean.setType("预算");
                        for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

                            if (k > 11 && k < 69 && row.getCell(k) != null) {
                                //单耗
                                Cell cell = row.getCell(k);
                                //单价
                                Cell cell_price = sheet.getRow(0).getCell(k);
                                //列名
                                Cell cell_name = sheet.getRow(3).getCell(k);
                                //一列数据作为一个对象插入
                                MaterialcostdetailsBean mcdb = new MaterialcostdetailsBean();
                                //名称
                                mcdb.setMaterialName(ExcelUtil.changetostring(cell_name, j, 3, k));
                                //单耗
                                if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell, j, i, k))) {
                                    mcdb.setConsumption(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell, j, i, k), j, i, k));
                                } else {
                                    continue;
                                }
                                //单价
                                if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price, j, 0, k))) {
                                    mcdb.setPrice(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_price, j, 0, k), j, 0, k));
                                } else {
                                    continue;
                                }
                                //单位成本
                                if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price, j, i, k)) && StringUtil.isNotEmpty(ExcelUtil.changetostring(cell, j, i, k))) {
                                    BigDecimal decimal = ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell, j, i, k), j, i, k).multiply(new BigDecimal(ExcelUtil.changenumbertostring(cell_price, j, 0, k)));
                                    mcdb.setUnitPrice(decimal);
                                } else {
                                    continue;
                                }
                                //聚酯or纺丝
                                //   mcdb.setField(sheet.getRow(4).getCell(k).getStringCellValue());
                                //将该列数据添加进mcdbList集合
                                for (FieldBean field : fields) {
                                    if (mcdb.getMaterialName().equals(field.getMaterialName())) {
                                        mcdb.setField(field.getField());
                                        budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(mcdb);
                                    }
                                }
//                            ArrayList<String> fields=financeDataMapper.selectfieldbymaterialname(mcdb.getMaterialName());
//                            if (fields.size()>0){
//                                mcdb.setField(fields.get(0));
//                                budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(mcdb);
//                            }
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

                        budgetdetailBean.setCompany(ExcelUtil.changeinttostring(cell_company, j, i, 0));

                        if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_month, j, i, 1))) {
                            budgetdetailBean.setMonth(ExcelUtil.changeToBigDecimal(ExcelUtil.changeinttostring(cell_month, j, i, 1), j, i, 1));
                        }
                        if (StringUtil.isNotEmpty(ExcelUtil.changeinttostring(cell_year, j, i, 2))) {
                            budgetdetailBean.setYear(ExcelUtil.changeToBigDecimal(ExcelUtil.changeinttostring(cell_year, j, i, 2), j, i, 2));
                        }
                        budgetdetailBean.setProduct(ExcelUtil.changetostring(cell_product, j, i, 3));
                        budgetdetailBean.setWorkshop(ExcelUtil.changetostring(cell_workshop, j, i, 4));
                        budgetdetailBean.setLine(ExcelUtil.changeinttostring(cell_line, j, i, 5));
                        budgetdetailBean.setSpec(ExcelUtil.changetostring(cell_spec, j, i, 6));
                        budgetdetailBean.setYarnkind(ExcelUtil.changetostring(cell_yarnkind, j, i, 7));

                        if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_aarate, j, i, 8))) {
                            budgetdetailBean.setAarate(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_aarate, j, i, 8), j, i, 8));
                        }
                        if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_fsrate, j, i, 9))) {
                            budgetdetailBean.setFsrate(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_fsrate, j, i, 9), j, i, 9));
                        }
                        if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_dayProduct, j, i, 10))) {
                            budgetdetailBean.setDayProduct(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_dayProduct, j, i, 10), j, i, 10));
                        }
                        if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_budgetTotalProduct, j, i, 11))) {
                            budgetdetailBean.setBudgetTotalProduct(ExcelUtil.changeToBigDecimal(ExcelUtil.changenumbertostring(cell_budgetTotalProduct, j, i, 11), j, i, 11));
                        }
                        String[] lines = new String[1];
                        //遍历一行中的所有的数据添加进budgetdetailBeanList集合
                        for (ProductMatchBean productMatchBean : productmatchlist) {
                            if (StringUtil.equals(productMatchBean.getProductLine(), budgetdetailBean.getLine(), productMatchBean.getCompany(), budgetdetailBean.getCompany())) {
                                lines = productMatchBean.getProductMatch().split("/");
                                break;
                            }
                        }

                        for (int k = 0; k < lines.length; k++) {
                            budgetdetailBean.setLine(lines[k]);
                            //设置一个标识用于判断是否重复
                            boolean flag= true;
//                      ArrayList<String>  result= financeDataMapper.selectbudgetdatabybean(budgetdetailBean);
                                for (BudgetdetailBean budgetdetailBean1 : budgetDataByMonths) {
                                    if (StringUtil.isNotEmpty(budgetdetailBean1.getCompany())&&
                                            StringUtil.isNotEmpty(budgetdetailBean1.getProduct())&&
                                            StringUtil.isNotEmpty(budgetdetailBean1.getWorkshop())&&
                                            StringUtil.isNotEmpty(budgetdetailBean1.getLine())&&
                                            StringUtil.isNotEmpty(budgetdetailBean1.getSpec())&&
                                            StringUtil.isNotEmpty(budgetdetailBean1.getYarnkind())){
                                        if (budgetdetailBean1.getCompany().equals(budgetdetailBean.getCompany()) &&
                                                budgetdetailBean1.getProduct().equals(budgetdetailBean.getProduct()) &&
                                                budgetdetailBean1.getWorkshop().equals(budgetdetailBean.getWorkshop()) &&
                                                budgetdetailBean1.getLine().equals(budgetdetailBean.getLine()) &&
                                                budgetdetailBean1.getSpec().equals(budgetdetailBean.getSpec()) &&
                                                budgetdetailBean1.getYarnkind().equals(budgetdetailBean.getYarnkind())) {
                                            flag=false;
                                        }
                                    }
                            }
                           if (flag){
                               for (MaterialcostdetailsBean materialcostdetailsBean : budgetdetailBean.getMaterialcostdetailsBeanArrayList()) {
                                   financeBudgetMapper.insertmaterialcostdetails(materialcostdetailsBean);
                               }
                               financeBudgetMapper.insertdetail(budgetdetailBean);
                               budgetdetailBeanList.add(budgetdetailBean);
                           }
                        }
                    }
                }
            }
            Date day2 = new Date();
            LoggerUtil.info("导入数据结束时间：" + df.format(day2));
        }catch (Exception e){
            LoggerUtil.error(LoggerUtil.getTrace(e));
        }

    }

    /***
     * 更新预算详情数据
     * @param mapList
     */
    @Override
    public void updateBudgetDetail(List<LinkedHashMap> mapList) {
        financeBudgetMapper.updateBudgetDetail(mapList);
        financeBudgetMapper.updateBudgetDetailPrice(mapList);
    }
}
