package com.hengyi.controller;

import com.hengyi.domain.DictionaryDomain;
import com.hengyi.domain.ResultDomain;
import com.hengyi.service.FinanceBudgetService;
import com.hengyi.util.*;
import com.hengyi.vo.AllCompanyResultVo;
import com.hengyi.vo.ConditionVo;
import com.sun.org.apache.bcel.internal.generic.NEW;
import jdk.internal.util.xml.impl.Input;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @author liuyuan
 * @create 2018-03-21 9:34
 * @description 财务预算系统的控制层
 **/
@Controller
@RequestMapping("/FinanceBudgetController")
public class FinanceBudgetController {
    //限定上传的文件必须是Excel
    private static Map<String, String> excelTypes = new HashMap<String, String>();
    static {
        excelTypes.put("XLS", "XLS");
        excelTypes.put("XLSX", "XLSX");
    }
    @Resource
    private FinanceBudgetService financeBudgetService;

    /***
     * 获取详细数据（预算和实际数据都有）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getDetailData",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Map<String,Object>> []> getDetailData(HttpServletRequest request,HttpServletResponse response){
        try {
            ConditionVo conditionVo = InputSteamToJSON.getParams(request.getInputStream(),ConditionVo.class);
            Long total= financeBudgetService.getTotalCount(conditionVo);
            Page page=new Page(conditionVo.getPageIndex(),conditionVo.getPageCount(),total);
            conditionVo.setOffset(page.getOffset());
            conditionVo.setLimit(page.getPageSize());
            List<Map<String,Object>> list=financeBudgetService.getDetailData(conditionVo);
            List<Map<String,Object>> [] result=new List[2];
            result[0]=list;
//            result[1]=financeBudgetService.getSumDetail(conditionVo);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG, result,page);
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
    public ServerResponse<List []> getDictionary(HttpServletRequest request,HttpServletResponse response){
        try {
            ConditionVo conditionVo=InputSteamToJSON.getParams(request.getInputStream(),ConditionVo.class);
            List<DictionaryDomain> list=financeBudgetService.getDictionary();
            List<Map<String,String>> workshopList = null;
            List<Map<String,String>> lineList=null;
            List<Map<String,String>> specList=null;
            if (!StringUtil.isEmpty(conditionVo.getCompany())&& !StringUtil.isEmpty(conditionVo.getProduct())){
                workshopList=financeBudgetService.getWorkshop(conditionVo);
            }
            if (!StringUtil.isEmpty(conditionVo.getWorkshop())){
                lineList=financeBudgetService.getLine(conditionVo);
            }
            if (!StringUtil.isEmpty(conditionVo.getProductLine())){
                specList=financeBudgetService.getSpec(conditionVo);
            }
            List [] array =new List[4];
            array[0]=list;
            array[1]=workshopList;
            array[2]=lineList;
            array[3]=specList;
            return  ServerResponse.createBySuccess(Const.SUCCESS_MSG,array);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 获取结果列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getResultData",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<ResultDomain>> getResult(HttpServletRequest request,HttpServletResponse response) {
        try {
            ConditionVo conditionVo =InputSteamToJSON.getParams(request.getInputStream(),ConditionVo.class);
            long total =financeBudgetService.getResultCount(conditionVo);
            Page page=new Page(conditionVo.getPageIndex(),conditionVo.getPageCount(),total);
            conditionVo.setOffset(page.getOffset());
            conditionVo.setLimit(page.getPageSize());
            List<ResultDomain> list=financeBudgetService.getResult(conditionVo);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,list,page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 获取公司维度的结果
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAllCompanyData",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List> getAllCompanyData(HttpServletRequest request,HttpServletResponse response) {
        try {
            ConditionVo conditionVo =InputSteamToJSON.getParams(request.getInputStream(),ConditionVo.class);
            long total = financeBudgetService.getAllCompanyDataCount(conditionVo);
            Page page=new Page(conditionVo.getPageIndex(),conditionVo.getPageCount(),total);
            conditionVo.setOffset(page.getOffset());
            conditionVo.setLimit(page.getPageSize());
            List<AllCompanyResultVo> list = financeBudgetService.getAllCompanyData(conditionVo);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,list,page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 导入预算数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importBudgetData")
    @ResponseBody
    public ServerResponse<Integer> importBudgetData(HttpServletRequest request,HttpServletResponse response){
        try {
            //创建一个解析器,并前端请求中解析文本
            CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
            //设置编码为utf-8
            commonsMultipartResolver.setDefaultEncoding("utf-8");
            if (commonsMultipartResolver.isMultipart(request)) {
                //转换成多部分request
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
                Iterator<String> fileNames=multipartRequest.getFileNames();
                // file 是指 文件上传标签的 name=值
                // 根据 name 获取上传的文件...
                while (fileNames.hasNext()){
                    MultipartFile multipartFile = multipartRequest.getFile(fileNames.next());
                    if (multipartFile !=null){
                        //取得当前上传文件的文件名称
                        String myFileName=multipartFile.getOriginalFilename();
                        //如果文件存在
                        if (myFileName.trim() != ""){
                            //获取文件后缀名必须为Excel的后缀名，否则导入失败
                            String fileType = myFileName.substring(myFileName.lastIndexOf(".") + 1).toUpperCase();
                            if (!excelTypes.containsKey(fileType)) {
                                return ServerResponse.createByError(Const.FAIL_MSG);
                            }
                            //创建一个缓存临时文件
                            File file=File.createTempFile(myFileName,fileType);
                            //将multipartFile转为file
                            multipartFile.transferTo(file);
                            financeBudgetService.importBudgetData(file);
                            //程序退出时将临时文件删除
                            file.deleteOnExit();
                        }
                    }
                }
            }
//            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }
}
