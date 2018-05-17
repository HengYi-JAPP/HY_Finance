package com.hengyi.service;

import com.hengyi.domain.MaterialMatchDomain;
import com.hengyi.util.QueryDtoBase;

import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-17 9:35
 * @description 物料管理界面的服务
 **/
public interface MaterialManageService {
    /***
     * 获取未匹配到物料记录数，从而便于进行分页
     * @return
     */
    Long getUnmatchedMaterialCount();
    /***
     * 获取未匹配到的物料信息
     * @return
     */
    List<MaterialMatchDomain> getUnmatchedMaterial(QueryDtoBase queryDtoBase);

    /***
     * 获取已经匹配上的物料匹配关系记录数
     */
    Long getMatchedMaterialCount();

    /***
     * 获取已经有的匹配关系
     * @return
     */
    List<MaterialMatchDomain> getMatchedMaterial(QueryDtoBase queryDtoBase);

    /***
     * 添加物料匹配关系
     * @param materialMatchDomain
     */
    void addMatchedMaterial(MaterialMatchDomain materialMatchDomain);

    /***
     * 修改物料匹配关系
     * @param materialMatchDomain
     */
    void updateMatchedMaterial(MaterialMatchDomain materialMatchDomain);

    /***
     * 删除物料匹配关系
     * @param materialMatchDomain
     */
    void deleteMatchedMaterial(MaterialMatchDomain materialMatchDomain);
}
