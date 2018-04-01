package com.hengyi.mapper;

import com.hengyi.bean.BudgetdetailBean;
import com.hengyi.bean.MaterialcostdetailsBean;
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
     * 获取字典表数据
     * @return
     */
    List<DictionaryDomain> getDictionary();

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
     * 获取所有公司粗略的均值
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

    void insertdetail(@Param("bdb") BudgetdetailBean budgetdetailBean);
}