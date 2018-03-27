package com.hengyi.controller;

import com.hengyi.domain.DictionaryDomain;
import com.hengyi.service.FinanceBudgetService;
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
import java.util.Map;

/**
 * @author liuyuan
 * @create 2018-03-21 9:34
 * @description 财务预算系统的控制层
 **/
@Controller
@RequestMapping("/FinanceBudgetController")
public class FinanceBudgetController {
    @Resource
    private FinanceBudgetService financeBudgetService;
    /***
     * 获取对比结果数据
     * @param request
     * @param response
     * @return
     */
//    @RequestMapping(value = "/getResultData")
//    @ResponseBody
//    public ServerResponse<TestDomain> getResultData(HttpServletRequest request, HttpServletResponse response){
//        try {
//            System.out.println(request.getInputStream());
//            TestDomain testDomain = InputSteamToJSON.getParams(request.getInputStream(),TestDomain.class);
//           return ServerResponse.createBySuccess(Const.SUCCESS_MSG, testDomain);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return ServerResponse.createByError(Const.FAIL_MSG);
//    }

    /***
     * 获取详细数据（预算和实际数据都有）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDetailData",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Map<String,Object>>> getDetailData(HttpServletRequest request,HttpServletResponse response){
        try {
            ConditionVo conditionVo = InputSteamToJSON.getParams(request.getInputStream(),ConditionVo.class);
            Long total= financeBudgetService.getTotalCount(conditionVo);
            Page page=new Page(conditionVo.getPageIndex(),conditionVo.getPageCount(),total);
            conditionVo.setOffset(page.getOffset());
            conditionVo.setLimit(page.getPageSize());
            List<Map<String,Object>> list=financeBudgetService.getDetailData(conditionVo);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG, list,page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 获取字典数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDictionary",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<DictionaryDomain>> getDictionary(HttpServletRequest request,HttpServletResponse response){
        try {
            List<DictionaryDomain> list=financeBudgetService.getDictionary();
            return  ServerResponse.createBySuccess(Const.SUCCESS_MSG,list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }
}
