package com.hengyi.service;

import com.hengyi.domain.DetailAddDomain;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    // 获取成本大类（分聚酯阶段和纺丝阶段）
    List<Map<String,Object>> getCostItem(ConditionVo conditionVo);
    // 获取成本大类（不分聚酯阶段和纺丝阶段）
    List<Map<String,Object>> getSumCostItem(ConditionVo conditionVo);

    // 获取详情合计的均值
    List<Map<String,Object>> getSumDetail(ConditionVo conditionVo);
    // 获取概览合计的均值
    List<Map<String,Object>> getSumOverview(ConditionVo conditionVo);

    //获取字典表数据
    List<DictionaryDomain> getDictionary();
    /***
     * 获取车间列表
     * @param conditionVo
     * @return
     */
    List<Map<String,String>> getWorkshop(ConditionVo conditionVo);

    /***
     * 获取生产线列表
     * @param conditionVo
     * @return
     */
    List<Map<String,String>> getLine(ConditionVo conditionVo);

    /***
     * 获取规格列表
     * @param conditionVo
     * @return
     */
    List<Map<String,String>> getSpec(ConditionVo conditionVo);

    // 获取总记录数
    Long getTotalCount(ConditionVo conditionVo);

    // 获取结果数据
    List<ResultDomain> getResult(ConditionVo conditionVo);

    // 获取结果总记录数
    Long getResultCount(ConditionVo conditionVo);

    // 获取公司维度的数据
    List<AllCompanyResultVo> getAllCompanyData(ConditionVo conditionVo);

    //获取新增规格的总记录数
    Long getNewlyIncreasedCount(ConditionVo conditionVo);
    //获取新增规格的
    List<DetailAddDomain> getNewlyIncreased(ConditionVo conditionVo);

    // 获取公司维度均值记录数
    Long getAllCompanyDataCount(ConditionVo conditionVo);
    //导入预算数据
    void importBudgetData(File file) throws IOException, InvalidFormatException;
    //根据条件导出详情表数据
     void exportExcel(ConditionVo conditionVo) throws FileNotFoundException;
     //根据条件导出成本项大类的数据
     String exportOverviewExcel(ConditionVo conditionVo) throws IOException;
     //根据条件导出公司维度的数据
     String exportAllCompanyExcel(ConditionVo conditionVo) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
     //根据条件导出当个规格的数据
     String exportResultExcel(ConditionVo conditionVo) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    //将导入的Excel表格的预算数据插入到数据库中
    void insertBudgetData();
}