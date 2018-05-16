package com.hengyi.sapmapper;

import com.hengyi.bean.FactProductBean;
import com.hengyi.vo.ConditionVo;

import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-14 22:08
 * @description
 **/
public interface SAPFinanceBudgetMapper {
    /***
     * 获取SAP实际产量
     * @param conditionVo
     * @return
     */
    List<FactProductBean> getSapFact(ConditionVo conditionVo);

    /***
     * 获取实际产量记录数
     * @param conditionVo
     * @return
     */
    Long getSapFactCount(ConditionVo conditionVo);
}
