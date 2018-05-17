package com.hengyi.mapper;

import com.hengyi.domain.MaterialMatchDomain;
import com.hengyi.util.QueryDtoBase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-17 9:54
 * @description 物料匹配关系管理
 **/
@Repository
public interface MaterialManageMapper {
    /***
     * 获取为匹配到的物料信息的记录数
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
     * @return
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

    /***
     * 当成功新增物料匹配关系之后，会把未匹配的物料标识为已经匹配上，即把删除标志del_flag改为1
     * @param materialMatchDomain
     */
    void updateUnmatchedMaterial(MaterialMatchDomain materialMatchDomain);
}
