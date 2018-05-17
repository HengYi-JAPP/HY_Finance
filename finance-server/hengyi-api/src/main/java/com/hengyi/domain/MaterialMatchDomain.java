package com.hengyi.domain;

/**
 * @author liuyuan
 * @create 2018-05-17 9:27
 * @description 物料匹配关系实体类
 **/
public class MaterialMatchDomain {
    // 主键id
    Integer id;
    //物料名称
    String materialName;
    //物料号
    String materialId;
    //物料类型
    String materialCategory;
    //成本要素id
    String costId;
    //对应的字段名称
    String field;
    //阶段
    String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(String materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
