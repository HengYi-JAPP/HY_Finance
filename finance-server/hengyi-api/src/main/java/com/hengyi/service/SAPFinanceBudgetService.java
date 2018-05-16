package com.hengyi.service;

import com.hengyi.bean.FactProductBean;
import com.hengyi.vo.ConditionVo;

import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-15 8:11
 * @description 从SAP获取数据的服务接口
 **/
public interface SAPFinanceBudgetService {
    /***
     * 获取SAP实际产量
     * @param conditionVo
     * @return
     */
    List<FactProductBean> getSapFact(ConditionVo conditionVo);

    /***
     * 获取SAP实际产量的记录数
     * @param conditionVo
     * @return
     */
    Long getSapFactCount(ConditionVo conditionVo);
}
