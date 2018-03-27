package com.hengyi.service;

import com.hengyi.domain.DictionaryDomain;
import com.hengyi.vo.ConditionVo;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @create 2018-03-22 15:16
 * @description 预算模块相关服务
 **/
public interface FinanceBudgetService {
    // 获取详情表数据
    List<Map<String,Object>> getDetailData(ConditionVo conditionVo);
    //获取字典表数据
    List<DictionaryDomain> getDictionary();
    // 获取总记录数
    Long getTotalCount(ConditionVo conditionVo);
}
