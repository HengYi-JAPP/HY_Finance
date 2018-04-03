package com.hengyi.mapper;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.bean.ProductMatchBean;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @create 2018-03-22 16:21
 * @description
 **/
@Repository
public interface FinanceBudgetMapper {
    /***
     * 获取一个详情列表数据
     * @param conditionVo
     * @return
     */
    List<Map<String, Object>> getDetailData(ConditionVo conditionVo);

    /***
     * 获取字典表数据（公司列表和产品规格）
     * @return
     */
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

    /***
     * 获取总记录数
     * @param conditionVo
     * @return
     */
    Long getTotalCount(ConditionVo conditionVo);

    /***
     * 获取详情表值
     * @return
     */
    MaterialcostdetailsBean getCostDetail(Integer id);

    /***
     * 获取一个结果
     * @param conditionVo
     * @return
     */
    List<ResultDomain> getResult(ConditionVo conditionVo);

    /***
     * 获取结果记录数
     * @param conditionVo
     * @return
     */
    Long getResultCount(ConditionVo conditionVo);

    /***
     * 获取公司维度均值
     * @param conditionVo
     * @return
     */
    List<AllCompanyResultVo> getAllCompanyData(ConditionVo conditionVo);

    /***
     * 获取所有公司粗略的记录数
     * @param conditionVo
     * @return
     */
    Long getAllCompanyDataCount(ConditionVo conditionVo);

    ArrayList<String> selectfieldbymaterialname(String materialName);

    void insertmaterialcostdetails(MaterialcostdetailsBean materialcostdetailsBean);
    /**
     * 插入一条预算的详情数据到BudgetDetail表中
     *
     * @param
     * @return
     */
    void insertdetail(@Param("bdb") BudgetdetailBean budgetdetailBean);

    /**
     * 查找生产线匹配关系
     *
     * @param
     * @return
     */
    public ArrayList<ProductMatchBean>  selectproductmatch();
    /**
     * 插入预算数据时，先搜索判断该数据是否已插入
     *
     * @param
     * @return
     */
    public  ArrayList<String> selectbudgetdatabybean(BudgetdetailBean budgetdetailBean);
}