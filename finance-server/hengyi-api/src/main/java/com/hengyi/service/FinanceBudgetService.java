package com.hengyi.service;

import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @create 2018-03-22 15:16
 * @description 预算模块相关服务
 **/
public interface FinanceBudgetService {
    // 获取详情表数据
    List<Map<String, Object>> getDetailData(ConditionVo conditionVo);

    //获取字典表数据
    List<DictionaryDomain> getDictionary();

    // 获取总记录数
    Long getTotalCount(ConditionVo conditionVo);

    // 获取结果数据
    List<ResultDomain> getResult(ConditionVo conditionVo);

    // 获取结果总记录数
    Long getResultCount(ConditionVo conditionVo);

    // 获取公司维度的数据
    List<AllCompanyResultVo> getAllCompanyData(ConditionVo conditionVo);

    // 获取公司维度均值记录数
    Long getAllCompanyDataCount(ConditionVo conditionVo);

    void importBudgetData(File file) throws IOException, InvalidFormatException;
}