package com.hengyi.controller;

import com.hengyi.bean.FactProductBean;
import com.hengyi.service.SAPFinanceBudgetService;
import com.hengyi.util.Const;
import com.hengyi.util.InputSteamToJSON;
import com.hengyi.util.Page;
import com.hengyi.util.ServerResponse;
import com.hengyi.vo.ConditionVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-15 8:17
 * @description
 **/
@Controller
@RequestMapping(value = "/SapFinanceBudgetController")
public class SAPFinanceBudgetController {
    @Resource
    private SAPFinanceBudgetService sapFinanceBudgetService;

    /***
     * 从SAP的MiniData数据库获取实际产量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getCompareProduct",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<FactProductBean>> getSapFact(HttpServletRequest request, HttpServletResponse response){
        try {
            ConditionVo conditionVo= InputSteamToJSON.getParams(request.getInputStream(),ConditionVo.class);
//            Long total=sapFinanceBudgetService.getSapFactCount(conditionVo);
//            Page page=new Page(conditionVo.getPageIndex(),conditionVo.getPageCount(),total);
//            conditionVo.setOffset(page.getOffset());
//            conditionVo.setLimit(page.getPageSize());
            List<FactProductBean> list=sapFinanceBudgetService.getSapFact(conditionVo);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }
}
