package com.hengyi.bean;

public class MaterialMatchBean {
   private String materialId;
   private String materialName;
   private String materialCategory;
   private String costId;
   private String field;
   private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public MaterialMatchBean() {
    }

    public MaterialMatchBean(String materialId, String materialName, String materialCategory, String costId, String field) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialCategory = materialCategory;
        this.costId = costId;
        this.field = field;
    }
}
