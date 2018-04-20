package com.hengyi.service;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.bean.ProductMatchBean;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.mapper.FinanceBudgetMapper;
import com.hengyi.util.ExcelUtil;
import com.hengyi.util.StringUtil;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
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
        conditionVo.setPriceOrconsumer("cost");
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
        conditionVo.setPriceOrconsumer("cost");
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
                }else if (key.contains("hdl")){
                    HdlCount+=Double.parseDouble(detail.get(key).toString());
                    Count+=Double.parseDouble(detail.get(key).toString());
                }else if (key.contains("hsl")){
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
     * 获取详情合计的均值
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, Object>> getSumDetail(ConditionVo conditionVo) {
        //将分页去除查询出所有数据
        conditionVo.setOffset(null);
        conditionVo.setLimit(null);
        //存储实际均值
        HashMap<String,Object> fact=new LinkedHashMap<>();
        //存储预算均值
        HashMap<String,Object> budget=new LinkedHashMap<>();
        //存储差异均值
        HashMap<String,Object> difference=new LinkedHashMap<>();
        //存放均值结果（共三个map）
        List<Map<String,Object>> resultList=new ArrayList<Map<String, Object>>();
        //获取所有详情记录
        List<Map<String,Object>> list= financeBudgetMapper.getDetailData(conditionVo);
        //将详情记录id转化为相应值并按实际，预算，差异的顺序查找出来
        List<Map<String,Object>> mapList=getList(list,conditionVo);
        double sumFactProduct=0;
        String [] array=new String[]{"id","type","company","month","year","product","workshop","line","spec","yarnKind","AArate","FSrate","day_product","budget_total_product"};
        for (int i = 0; i < array.length; i++) {
            fact.put(array[i],mapList.get(0).get(array[i]));
            budget.put(array[i],mapList.get(0).get(array[i]));
            difference.put(array[i],mapList.get(0).get(array[i]));
        }
        //对实际进行求和
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
                            sumFactProduct=sumFactProduct+Double.parseDouble(mapList.get(i).get("budget_total_product").toString());
                        }else{//对预算进行求和
                            sumBudget=sumBudget+Double.parseDouble(mapList.get(i).get(key+"1").toString())*Double.parseDouble(mapList.get(i-1).get("budget_total_product").toString());
                        }
                    }
                }
                if (sumFactProduct==0){
                    fact.put(key,0);
                    budget.put(key,0);
                }else {
                   fact.put(key,sumFact/sumFactProduct);
                   budget.put(key,sumBudget/sumFactProduct);
                }
            }
        }
        resultList.add(fact);
        resultList.add(budget);
        return resultList;
//        // 获取所有实际的数据
//        conditionVo.setType("实际");
//        mapList = getOneTypeList(list, conditionVo);
//        //累计所有实际记录的总产量(由于无论预算还是实际计算均值都是用实际的总产量)
//        BigDecimal sumFactProduct=new BigDecimal(0);
//        for (Map map:mapList) {
//            if ((StringUtil.isEmpty(map.get("budget_total_product")))) {
//                sumFactProduct = sumFactProduct.add(new BigDecimal(map.get("budget_total_product").toString()));
//            } else {
//                continue;
//            }
//        }
//        resultList.add(getSum(mapList,sumFactProduct,fact));
//
//        //获取所有预算的数据
//        conditionVo.setType("预算");
//        mapList = getOneTypeList(list, conditionVo);
//        resultList.add(getSum(mapList,sumFactProduct,budget));
//        return mapList;
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
            vo.setPriceEffect(vo.getProductUnitPrice().subtract(vo.getCheckBudgetUnitPrice()));
            vo.setConsumerEffect(vo.getCheckProductUnitPrice().subtract(vo.getCheckBudgetUnitPrice()));
            vo.setStructureEffect(vo.getCheckBudgetUnitPrice().subtract(vo.getBudgetUnitPrice()));
            vo.setTotalDifference(vo.getProductUnitPrice().subtract(vo.getBudgetUnitPrice()));
        }
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
     * 导出Excel
     */
    @Override
    public void exportExcel() {

    }
}
