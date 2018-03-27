package com.hengyi.mapper;

import com.hengyi.bean.MaterialcostdetailsBean;
import com.hengyi.domain.DictionaryDomain;
import com.hengyi.vo.ConditionVo;
import org.springframework.stereotype.Repository;

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
}