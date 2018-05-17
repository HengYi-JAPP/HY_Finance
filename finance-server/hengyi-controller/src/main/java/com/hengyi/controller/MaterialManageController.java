package com.hengyi.controller;

import com.hengyi.domain.MaterialMatchDomain;
import com.hengyi.service.MaterialManageService;
import com.hengyi.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-17 9:10
 * @description 物料管理控制层
 **/
@Controller
@RequestMapping(value = "/MaterialManageController")
public class MaterialManageController {
    @Resource
    private MaterialManageService materialManageService;
    /***
     * 获取为匹配到的物料，从而方便物料
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUnmatchedMaterial")
    @ResponseBody
    public ServerResponse<List<MaterialMatchDomain>> getUnmatchedMaterial(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取页面上的分页信息
            QueryDtoBase queryDtoBase=InputSteamToJSON.getParams(request.getInputStream(),QueryDtoBase.class);
            Long total=materialManageService.getUnmatchedMaterialCount();
            Page page=new Page(queryDtoBase.getPageIndex(),queryDtoBase.getPageCount(),total);
            queryDtoBase.setOffset(page.getOffset());
            queryDtoBase.setLimit(page.getPageSize());
            List<MaterialMatchDomain> list= materialManageService.getUnmatchedMaterial(queryDtoBase);
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
    @RequestMapping(value = "/getMatchedMaterial")
    @ResponseBody
    public ServerResponse<List<MaterialMatchDomain>> getMatchedMaterial(HttpServletRequest request,HttpServletResponse response){
        try {
            //获取页面上的分页信息
            QueryDtoBase queryDtoBase=InputSteamToJSON.getParams(request.getInputStream(),QueryDtoBase.class);
            Long total=materialManageService.getMatchedMaterialCount();
            Page page=new Page(queryDtoBase.getPageIndex(),queryDtoBase.getPageCount(),total);
            queryDtoBase.setOffset(page.getOffset());
            queryDtoBase.setLimit(page.getPageSize());
            List<MaterialMatchDomain> list=materialManageService.getMatchedMaterial(queryDtoBase);
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
    @RequestMapping(value = "/addmatchedMaterial")
    @ResponseBody
    public ServerResponse addMatchedMaterial(HttpServletRequest request,HttpServletResponse response){
        try {
            MaterialMatchDomain materialMatchDomain= InputSteamToJSON.getParams(request.getInputStream(),MaterialMatchDomain.class);
            materialManageService.addMatchedMaterial(materialMatchDomain);
            ServerResponse.createBySuccess(Const.SUCCESS_MSG,true);
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
    @RequestMapping(value = "/updateMatchedMaterial")
    @ResponseBody
    public  ServerResponse updateMatchedMaterial(HttpServletRequest request,HttpServletResponse response){
        try {
            MaterialMatchDomain materialMatchDomain=InputSteamToJSON.getParams(request.getInputStream(),MaterialMatchDomain.class);
            materialManageService.updateMatchedMaterial(materialMatchDomain);
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
    @RequestMapping(value = "/deleteMatchMaterial")
    @ResponseBody
    public ServerResponse deleteMaterial(HttpServletRequest request,HttpServletResponse response){
        try {
            MaterialMatchDomain materialMatchDomain=InputSteamToJSON.getParams(request.getInputStream(),MaterialMatchDomain.class);
            materialManageService.deleteMatchedMaterial(materialMatchDomain);
            return ServerResponse.createBySuccess(Const.SUCCESS_MSG,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ServerResponse.createByError(Const.FAIL_MSG);
    }
}
