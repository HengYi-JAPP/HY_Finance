package com.hengyi.service;

import com.hengyi.bean.FactProductBean;
import com.hengyi.sapmapper.SAPFinanceBudgetMapper;
import com.hengyi.vo.ConditionVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-15 8:14
 * @description
 **/
public class SAPFinanceBudgetServiceImpl implements SAPFinanceBudgetService {
    @Autowired
    private SAPFinanceBudgetMapper sapFinanceBudgetMapper;

    /***
     * 获取SAP实际产量
     * @param conditionVo
     * @return
     */
    @Override
    public List<FactProductBean> getSapFact(ConditionVo conditionVo) {
        return sapFinanceBudgetMapper.getSapFact(conditionVo);
    }

    /***
     * 获取SAP实际产量记录数
     * @param conditionVo
     * @return
     */
    @Override
    public Long getSapFactCount(ConditionVo conditionVo) {
        return sapFinanceBudgetMapper.getSapFactCount(conditionVo);
    }
}
