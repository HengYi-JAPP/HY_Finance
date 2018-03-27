package com.hengyi.service;

import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.mapper.FinanceBudgetMapper;
import com.hengyi.util.StringUtil;
import com.hengyi.vo.ConditionVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author liuyuan
 * @create 2018-03-22 16:52
 * @description
 **/
public class FinanceBudgetServiceImpl implements FinanceBudgetService{
    @Autowired
    private FinanceBudgetMapper financeBudgetMapper;

    /***
     * 获取详细数据的方法
     * @param conditionVo
     * @return
     */
    @Override
    public List<Map<String, Object>> getDetailData(ConditionVo conditionVo) {
       List<Map<String,Object>> list= financeBudgetMapper.getDetailData(conditionVo);
        List<Map<String,Object>> mapList= new ArrayList<Map<String,Object>>();
        for (Map<String,Object> map:list) {
            Map<String,Object> hashMap= new LinkedHashMap<String, Object>();
            for (String key:map.keySet()) {
                if ("id".equals(key)){
                }else if ("type".equals(key)|"company".equals(key)|"month".equals(key)|"year".equals(key)|
                        "product".equals(key)|"workshop".equals(key)|"line".equals(key)
                        |"spec".equals(key)|"yarnKind".equals(key)|"AArate".equals(key)|"FSrate".equals(key)|"day_product".equals(key)|"budget_total_product".equals(key)|"".equals(key)) {
                    hashMap.put(key,map.get(key));
                }else{
                    if (StringUtil.isEmpty(map.get(key))){
                        hashMap.put(key,null);
                    }else {
                        MaterialcostdetailsBean materialcostdetailsBean= financeBudgetMapper.getCostDetail(Integer.parseInt(map.get(key).toString()));
                        if ("price".equals(conditionVo.getPriceOrconsumer())) {
                            hashMap.put(key,materialcostdetailsBean.getPrice());
                        }else if ("consumer".equals(conditionVo.getPriceOrconsumer())){
                            hashMap.put(key,materialcostdetailsBean.getConsumption());
                        }else if ("cost".equals(conditionVo.getPriceOrconsumer())){
                            hashMap.put(key,materialcostdetailsBean.getUnitPrice());
                        }
                    }
                }
            }
            mapList.add(hashMap);
        }
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

    @Override
    public Long getTotalCount(ConditionVo conditionVo) {
        return financeBudgetMapper.getTotalCount(conditionVo);
    }
}
