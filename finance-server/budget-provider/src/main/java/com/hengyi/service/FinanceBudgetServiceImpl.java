package com.hengyi.service;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.mapper.FinanceBudgetMapper;
import com.hengyi.util.ExcelUtil;
import com.hengyi.util.StringUtil;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
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
        if ("实际".equals(conditionVo.getType()) | "预算".equals(conditionVo.getType())) {
            mapList = getOneTypeList(list, conditionVo);
            return mapList;
        }
        mapList = getList(list, conditionVo);
        return mapList;
    }

    /***
     * 获取字典表数据
     * @return
     */
    @Override
    public List<DictionaryDomain> getDictionary() {
        return financeBudgetMapper.getDictionary();
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

//            resultDomain.setPriceEffect(resultDomain.getCheckProductUnitPrice().subtract(resultDomain.getProductUnitPrice()));
//            resultDomain.setConsumerEffect(resultDomain.getCheckBudgetUnitPrice().subtract(resultDomain.getCheckProductUnitPrice()));
//            resultDomain.setStructureEffect(resultDomain.getBudgetUnitPrice().subtract(resultDomain.getCheckBudgetUnitPrice()));
//            resultDomain.setTotalDifference(resultDomain.getBudgetUnitPrice().subtract(resultDomain.getProductUnitPrice()));
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

//            vo.setPriceEffect(vo.getCheckBudgetUnitPrice().subtract(vo.getProductUnitPrice()));
//            vo.setConsumerEffect(vo.getCheckBudgetUnitPrice().subtract(vo.getCheckProductUnitPrice()));
//            vo.setStructureEffect(vo.getBudgetUnitPrice().subtract(vo.getCheckBudgetUnitPrice()));
//            vo.setTotalDifference(vo.getBudgetUnitPrice().subtract(vo.getProductUnitPrice()));
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
        System.out.println("excel");
        // 创建文件流对象和工作簿对象
        // 文件流
        FileInputStream in = new FileInputStream(file);
        //工作簿
        Workbook book = WorkbookFactory.create(in);
        List<BudgetdetailBean> budgetdetailBeanList = new ArrayList<>();
        //j: sheet,  i: 行,  k: 列
        //遍历所有的sheet
        for (int j = 0; j < book.getNumberOfSheets(); j++) {
            // 获取当前excel中sheet的下标：0开始
            Sheet sheet = book.getSheetAt(j);   // 遍历Sheet
            //遍历所有的行和列
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    continue;
                }
                if (i >= 4 && row.getCell(0) != null && !ExcelUtil.changetostring(row.getCell(0)).equals("")) {
                    //一行数据作为一个对象插入
                    BudgetdetailBean budgetdetailBean = new BudgetdetailBean();
                    for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

                        if (68 > k && k > 11) {
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
                            } else {
                                continue;
                            }
                            //单价
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price))) {
                                mcdb.setPrice(new BigDecimal(ExcelUtil.changetostring(cell_price)));
                            } else {
                                continue;
                            }
                            //单位成本
                            if (StringUtil.isNotEmpty(ExcelUtil.changetostring(cell_price)) && StringUtil.isNotEmpty(ExcelUtil.changetostring(cell))) {
                                BigDecimal decimal = new BigDecimal(ExcelUtil.changetostring(cell)).multiply(new BigDecimal(ExcelUtil.changetostring(cell_price)));
                                mcdb.setUnitPrice(decimal);
                            } else {
                                continue;
                            }
                            //聚酯or纺丝
                            //   mcdb.setField(sheet.getRow(4).getCell(k).getStringCellValue());
                            //将该列数据添加进mcdbList集合
                            ArrayList<String> fields = financeBudgetMapper.selectfieldbymaterialname(mcdb.getMaterialName());
                            if (fields.size() > 0) {
                                mcdb.setField(fields.get(0));
                                budgetdetailBean.getMaterialcostdetailsBeanArrayList().add(mcdb);
                            }
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
                    financeBudgetMapper.insertmaterialcostdetails(materialcostdetailsBean);
                }
                financeBudgetMapper.insertdetail(budgetdetailBean);
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
                if ("id".equals(key) | "id1".equals(key)) {
                } else if ("type".equals(key)) {
                    hashMapFact.put(key, "实际");
                    hashMapDifference.put(key, "差异");
                } else if ("type1".equals(key)) {
                    hashMapBudget.put(key, "预算");
                } else if ("budget_total_product".equals(key)) {
                    hashMapFact.put(key,map.get(key));
                } else if ("budget_total_product1".equals(key)) {
                    hashMapBudget.put(key,map.get(key));
                    StringBuilder sb=new StringBuilder(key);
                    hashMapDifference.put(key,null);
//                    hashMapDifference.put(key,new BigDecimal(hashMapFact.get(sb.deleteCharAt(sb.length()-1).toString()).toString()).subtract(new BigDecimal(hashMapBudget.get(key).toString())));
                } else if ("company".equals(key) | "month".equals(key) | "year".equals(key) |
                        "product".equals(key) | "workshop".equals(key) | "line".equals(key)
                        | "spec".equals(key) | "yarnKind".equals(key) | "AArate".equals(key) |
                        "FSrate".equals(key) | "day_product".equals(key)) {
                    hashMapFact.put(key, map.get(key));
                    hashMapDifference.put(key, null);
                } else if ("company1".equals(key) | "month1".equals(key) | "year1".equals(key) |
                        "product1".equals(key) | "workshop1".equals(key) | "line1".equals(key)
                        | "spec1".equals(key) | "yarnKind1".equals(key) | "AArate1".equals(key) |
                        "FSrate1".equals(key) | "day_product1".equals(key)) {
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
                if ("id".equals(key) | "id1".equals(key)) {
                } else if ("type".equals(key) | "company".equals(key) | "month".equals(key) | "year".equals(key) |
                        "product".equals(key) | "workshop".equals(key) | "line".equals(key)
                        | "spec".equals(key) | "yarnKind".equals(key) | "AArate".equals(key) | "FSrate".equals(key) | "day_product".equals(key) | "budget_total_product".equals(key)) {
                    hashMap.put(key, map.get(key));
                } else {
                    hashMap = getValue(hashMap, key, conditionVo, map);
                }
            }
            mapList.add(hashMap);
        }
        return mapList;
    }

}
