package com.hengyi.service;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.mapper.FinanceBudgetMapper;
import com.hengyi.util.StringUtil;
import com.hengyi.vo.ConditionVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FinanceBudgetServiceImplTest {
    @Autowired
    private FinanceBudgetMapper financeBudgetMapper;
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

}