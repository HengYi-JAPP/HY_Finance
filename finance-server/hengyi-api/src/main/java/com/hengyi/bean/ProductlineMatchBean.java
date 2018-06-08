package com.hengyi.bean;

public class ProductlineMatchBean {
    Integer id; //主键Id
    String productLine; //生产线名称
    String productMatch; //生产线匹配关系
    String productMaterialMatch; //生产线物料匹配关系
    String productMaterialYarn; //产品物料纱种
    String company; //公司
    String productSpecificationsMatch; //产品规格匹配关系
    String productSpecificationsYarn; //生产规格纱种

    public ProductlineMatchBean(Integer id, String productLine, String productMatch, String productMaterialMatch, String productMaterialYarn, String company, String productSpecificationsMatch, String productSpecificationsYarn) {
        this.id = id;
        this.productLine = productLine;
        this.productMatch = productMatch;
        this.productMaterialMatch = productMaterialMatch;
        this.productMaterialYarn = productMaterialYarn;
        this.company = company;
        this.productSpecificationsMatch = productSpecificationsMatch;
        this.productSpecificationsYarn = productSpecificationsYarn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductMatch() {
        return productMatch;
    }

    public void setProductMatch(String productMatch) {
        this.productMatch = productMatch;
    }

    public String getProductMaterialMatch() {
        return productMaterialMatch;
    }

    public void setProductMaterialMatch(String productMaterialMatch) {
        this.productMaterialMatch = productMaterialMatch;
    }

    public String getProductMaterialYarn() {
        return productMaterialYarn;
    }

    public void setProductMaterialYarn(String productMaterialYarn) {
        this.productMaterialYarn = productMaterialYarn;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProductSpecificationsMatch() {
        return productSpecificationsMatch;
    }

    public void setProductSpecificationsMatch(String productSpecificationsMatch) {
        this.productSpecificationsMatch = productSpecificationsMatch;
    }

    public String getProductSpecificationsYarn() {
        return productSpecificationsYarn;
    }

    public void setProductSpecificationsYarn(String productSpecificationsYarn) {
        this.productSpecificationsYarn = productSpecificationsYarn;
    }
}
