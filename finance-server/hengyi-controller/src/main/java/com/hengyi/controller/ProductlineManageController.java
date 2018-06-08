package com.hengyi.controller;

import com.hengyi.domain.ProductlineMatchDomain;
import com.hengyi.service.ProductlineManageService;
import com.hengyi.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/ProductlineManageController")
public class ProductlineManageController {
    @Resource
    private ProductlineManageService productlineManageService;
    /***
     * 获取为匹配到的物料，从而方便物料
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUnmatchedProductline")
    @ResponseBody
    public ServerResponse<List<ProductlineMatchDomain>> getUnmatchedMaterial(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取页面上的分页信息
            QueryDtoBase queryDtoBase= InputSteamToJSON.getParams(request.getInputStream(),QueryDtoBase.class);
            Long total=productlineManageService.getMatchedProductlineCount();
            Page page=new Page(queryDtoBase.getPageIndex(),queryDtoBase.getPageCount(),total);
            queryDtoBase.setOffset(page.getOffset());
            queryDtoBase.setLimit(page.getPageSize());
            List<ProductlineMatchDomain> list= productlineManageService.getUnmatchedProductline(queryDtoBase);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,list,page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 获取已经匹配到的物料关系信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMatchedProductline")
    @ResponseBody
    public ServerResponse<List<ProductlineMatchDomain>> getMatchedProductline(HttpServletRequest request,HttpServletResponse response){
        try {
            //获取页面上的分页信息
            QueryDtoBase queryDtoBase=InputSteamToJSON.getParams(request.getInputStream(),QueryDtoBase.class);
            Long total=productlineManageService.getMatchedProductlineCount();
            Page page=new Page(queryDtoBase.getPageIndex(),queryDtoBase.getPageCount(),total);
            queryDtoBase.setOffset(page.getOffset());
            queryDtoBase.setLimit(page.getPageSize());
            List<ProductlineMatchDomain> list=productlineManageService.getMatchedProductline(queryDtoBase);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,list,page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 添加物料匹配关系
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addMatchedProductline")
    @ResponseBody
    public ServerResponse addMatchedProductline(HttpServletRequest request,HttpServletResponse response){
        try {
            ProductlineMatchDomain productlineMatchDomain= InputSteamToJSON.getParams(request.getInputStream(),ProductlineMatchDomain.class);
            productlineManageService.addMatchedProductline(productlineMatchDomain);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 修改物料匹配关系
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateMatchedProductline")
    @ResponseBody
    public  ServerResponse updateMatchedProductline(HttpServletRequest request,HttpServletResponse response){
        try {
            ProductlineMatchDomain productlineMatchDomain=InputSteamToJSON.getParams(request.getInputStream(),ProductlineMatchDomain.class);
            productlineManageService.updateMatchedProductline(productlineMatchDomain);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }

    /***
     * 删除物料匹配关系
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteMatchedProductline")
    @ResponseBody
    public ServerResponse deleteProductline(HttpServletRequest request,HttpServletResponse response){
        try {
            ProductlineMatchDomain productlineMatchDomain=InputSteamToJSON.getParams(request.getInputStream(),ProductlineMatchDomain.class);
            productlineManageService.deleteMatchedProductline(productlineMatchDomain);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }
}
