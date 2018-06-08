package com.hengyi.service;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.domain.ResultDomain;
import com.hengyi.mapper.FinanceBudgetMapper;
//import com.hengyi.mapper.FinanceDataMapper;
import com.hengyi.util.Page;
import com.hengyi.util.StringUtil;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FinanceBudgetServiceImplTest {
    @Autowired
    private FinanceBudgetMapper financeBudgetMapper;
    @Autowired
//    private FinanceDataMapper financeDataMapper;
    @Test
    public void getDetailData() throws Exception {
        ConditionVo conditionVo=new ConditionVo();
//        conditionVo.setCompany("2000");
//        conditionVo.setYear(null);
//        conditionVo.setMonth(2);
//        conditionVo.setProduct("FDY");
        conditionVo.setLimit(50);
//        conditionVo.setOffset(0);
        List<Map<String,Object>> list=financeBudgetMapper.getDetailData(conditionVo);
        for (Map<String,Object> map:list) {
            for (Object o:map.values()) {
                System.out.println(o);
            }
        }
        List<Map<String,Object>> mapList= new ArrayList<Map<String,Object>>();
//        for (Map<String,Object> map:list) {
//            Map<String,Object> hashMap= new HashMap<String, Object>();
//            for (String key:map.keySet()) {
//                if ("id".equals(key)){
//                }else if ("type".equals(key)|"company".equals(key)|"month".equals(key)|"year".equals(key)|
//                        "product".equals(key)|"workshop".equals(key)|"line".equals(key)
//                        |"spec".equals(key)|"yarnKind".equals(key)|"AArate".equals(key)|"FSrate".equals(key)|"day_product".equals(key)|"budget_total_product".equals(key)|"".equals(key)) {
//                    hashMap.put(key,map.get(key));
//                }else{
//                    if (StringUtil.isEmpty(map.get(key))){
//                        hashMap.put(key,null);
//                    }else {
////                        System.out.println("key:"+key+"value"+map.get(key));
//                        MaterialcostdetailsBean materialcostdetailsBean= financeBudgetMapper.getCostDetail(Integer.parseInt(map.get(key).toString()));
//                        hashMap.put(key,materialcostdetailsBean.getConsumption());
//                    }
//                }
//            }
//            mapList.add(hashMap);
////            for (Object o: map.values()) {
////                String s= o.toString();
////                System.out.println(s);
////            }
//        }
//        for (Map<String,Object> map: mapList) {
//            for (Object o:map.values()) {
//                System.out.println(o);
//            }
//        }

    }
    @Test
    public void getResult() {
        ConditionVo conditionVo=new ConditionVo();
        List<ResultDomain> list = financeBudgetMapper.getResult(conditionVo);
        for (ResultDomain result:list) {
            System.out.println(result.getBudgetUnitPrice());
        }
    }
    @Test
    public void getResult2() {
        ConditionVo conditionVo=new ConditionVo();
        conditionVo.setPriceOrconsumer("price");
        conditionVo.setLimit(10);
        conditionVo.setOffset(10);
        conditionVo.setPageIndex(2);
        conditionVo.setPageCount(10);
        List<Map<String, Object>> list = financeBudgetMapper.getDetailData(conditionVo);
//        for (Map<String,Object> map:list) {
            for (String key: list.get(0).keySet()) {
                System.out.println("key:" + key);
            }
//        }
    }
    @Test
    public void testExportExcel(){
        HSSFWorkbook book=new HSSFWorkbook();
        HSSFSheet sheet=book.createSheet("测试表格");
        ConditionVo vo=new ConditionVo();
//        vo.setType("实际");
        vo.setPriceOrconsumer("price");
        vo.setOffset(10);
        vo.setLimit(10);
        List<Map<String,Object>> list=getDetailData(vo);
        for (Map<String,Object> map: list) {
            for (String key: map.keySet()) {
                System.out.println("key:"+key+"-----"+"value:"+map.get(key));
            }

        }
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
     * 获取详细数据的方法
     * @param conditionVo
     * @return
     */
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



//    @Test
//    public void exportexcel () throws Exception {
//        ArrayList<LinkedHashMap<String, Object>> budgetresult = financeDataMapper.selectproductbudgetdata();
//        String filepath = "D:\\测试导出3.xlsx";
//        File file = new File(filepath);
//        FileInputStream in = new FileInputStream(file);
//        Workbook book = WorkbookFactory.create(in); //工作簿
//        System.out.println("开始导出");
////        FileOutputStream out = null;
//        try {
//            //添加表头
//            int num2 = 0;
//            //表头的列标
//            int i=0;
//            Row row0=book.getSheetAt(0).createRow(num2);
//            for (String key:budgetresult.get(0).keySet()) {
//                if ("id".equals(key)) {
//                } else if ("type".equals(key) || "company".equals(key) || "month".equals(key) || "year".equals(key) ||
//                        "product".equals(key) || "workshop".equals(key) || "line".equals(key)
//                        || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) || "FSrate".equals(key) || "day_product".equals(key) || "budget_total_product".equals(key)) {
//                    Cell cell=row0.createCell(i++);
//                    cell.setCellValue(key);
//                }else {
//                    Cell cell0=row0.createCell(i++);
//                    cell0.setCellValue(key);
//                    Cell cell1=row0.createCell(i++);
//                    cell1.setCellValue("单位成本");
//                    Cell cell2=row0.createCell(i++);
//                    cell2.setCellValue("单价");
//                    Cell cell3=row0.createCell(i++);
//                    cell3.setCellValue("单耗");
//                }
//
//            }
//            //开始添加数据
//            int num3=1;
//            for (LinkedHashMap<String,Object> map : budgetresult) {
//                Row row=book.getSheetAt(0).createRow(num3++);
//                //数据行的列标
//                int j =0;
//                for (String key : map.keySet()) {
//                    if ("id".equals(key)) {
//                    } else if ("type".equals(key) || "company".equals(key) || "month".equals(key) || "year".equals(key) ||
//                            "product".equals(key) || "workshop".equals(key) || "line".equals(key)
//                            || "spec".equals(key) || "yarnKind".equals(key) || "AArate".equals(key) || "FSrate".equals(key) || "day_product".equals(key) || "budget_total_product".equals(key)) {
//                        Cell cell=row.createCell(j++);
//                        if (!StringUtil.isEmpty(map.get(key))){
//                            cell.setCellValue(map.get(key).toString());
//                        }else {
//                            cell.setCellValue("");
//                        }
//                    } else {
//                        if (!StringUtil.isEmpty(map.get(key))){
//                            MaterialcostdetailsBean materialcostdetailsBean = financeDataMapper.selectcostdetailbyid(Integer.parseInt(map.get(key).toString()));
//                            Cell cell0 = row.createCell(j++);
//                            cell0.setCellValue(materialcostdetailsBean.getMaterialName());
//                            Cell cell1=row.createCell(j++);
//                            cell1.setCellValue(materialcostdetailsBean.getUnitPrice().toString());
//                            Cell cell2=row.createCell(j++);
//                            cell2.setCellValue(materialcostdetailsBean.getPrice().toString());
//                            Cell cell3=row.createCell(j++);
//                            cell3.setCellValue(materialcostdetailsBean.getConsumption().toString());
//                        }else {
//                            Cell cell0 = row.createCell(j++);
//                            cell0.setCellValue("");
//                            Cell cell1 = row.createCell(j++);
//                            cell1.setCellValue("");
//                            Cell cell2 = row.createCell(j++);
//                            cell2.setCellValue("");
//                            Cell cell3 = row.createCell(j++);
//                            cell3.setCellValue("");
//                        }
//                    }
//                }
//                System.out.println(num3);
//            }
//            FileOutputStream out = new FileOutputStream(filepath);
//            book.write(out);
//            Date day=new Date();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println("结束时间："+df.format(day));
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
@Test
    public void test(){
        String t="111  ";
//        System.out.println(new BigDecimal(t));
        System.out.println(new BigDecimal(t.trim()));
    }
}