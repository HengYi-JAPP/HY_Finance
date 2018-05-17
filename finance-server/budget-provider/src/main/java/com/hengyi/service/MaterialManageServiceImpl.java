package com.hengyi.service;

import com.hengyi.domain.MaterialMatchDomain;
import com.hengyi.mapper.MaterialManageMapper;
import com.hengyi.util.QueryDtoBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liuyuan
 * @create 2018-05-17 9:46
 * @description 物料匹配关系管理服务接口实现类
 **/
public class MaterialManageServiceImpl implements MaterialManageService {
    @Autowired
    private MaterialManageMapper materialManageMapper;

    @Override
    public Long getUnmatchedMaterialCount() {
        return materialManageMapper.getUnmatchedMaterialCount();
    }

    /***
     * 获取未匹配上的物料匹配关系
     * @return
     */
    @Override
    public List<MaterialMatchDomain> getUnmatchedMaterial(QueryDtoBase queryDtoBase) {
        return materialManageMapper.getUnmatchedMaterial(queryDtoBase);
    }

    @Override
    public Long getMatchedMaterialCount() {
        return materialManageMapper.getMatchedMaterialCount();
    }

    /***
     * 获取已经匹配上的物料匹配关系信息
     * @return
     */
    @Override
    public List<MaterialMatchDomain> getMatchedMaterial(QueryDtoBase queryDtoBase) {
        return materialManageMapper.getMatchedMaterial(queryDtoBase);
    }

    /***
     * 增加物料匹配关系
     * @param materialMatchDomain
     */
    @Override
    public void addMatchedMaterial(MaterialMatchDomain materialMatchDomain) {
        //新添加物料匹配关系
        materialManageMapper.addMatchedMaterial(materialMatchDomain);
        //当新增物料匹配关系成功的时候需要将为匹配物料匹配关系表改为已经匹配上，即把状态改为1
        materialManageMapper.updateUnmatchedMaterial(materialMatchDomain);
    }

    /***
     * 修改物料匹配关系
     * @param materialMatchDomain
     */
    @Override
    public void updateMatchedMaterial(MaterialMatchDomain materialMatchDomain) {
        materialManageMapper.updateMatchedMaterial(materialMatchDomain);
    }

    /***
     * 删除物料匹配关系
     * @param materialMatchDomain
     */
    @Override
    public void deleteMatchedMaterial(MaterialMatchDomain materialMatchDomain) {
        materialManageMapper.deleteMatchedMaterial(materialMatchDomain);
    }
}
