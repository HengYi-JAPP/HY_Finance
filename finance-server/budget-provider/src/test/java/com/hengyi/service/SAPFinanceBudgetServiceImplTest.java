package com.hengyi.service;

import com.hengyi.bean.FactProductBean;
import com.hengyi.sapmapper.SAPFinanceBudgetMapper;
import com.hengyi.vo.ConditionVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SAPFinanceBudgetServiceImplTest {
    @Autowired
    private SAPFinanceBudgetMapper sapFinanceBudgetMapper;

    @Test
    public void getSapFact() throws Exception {
        ConditionVo conditionVo=new ConditionVo();
        conditionVo.setLimit(10);
        conditionVo.setOffset(10);
        conditionVo.setPageIndex(2);
        conditionVo.setPageCount(10);
        conditionVo.setStartMonth("2018-4");
        conditionVo.setEndMonth("2018-4");
        List<FactProductBean> list=sapFinanceBudgetMapper.getSapFact(conditionVo);
        for (FactProductBean factProductBean:list) {
            System.out.println(factProductBean.getCompany());
        }
    }

    @Test
    public void getSapFactCount() throws Exception {
        ConditionVo conditionVo=new ConditionVo();
        conditionVo.setLimit(10);
        conditionVo.setOffset(10);
        conditionVo.setPageIndex(2);
        conditionVo.setPageCount(10);
        conditionVo.setStartMonth("2018-4");
        conditionVo.setEndMonth("2018-4");
        Long result=sapFinanceBudgetMapper.getSapFactCount(conditionVo);
        System.out.println(result);
    }

}