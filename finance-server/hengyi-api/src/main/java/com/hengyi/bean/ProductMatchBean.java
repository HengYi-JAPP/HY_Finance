package com.hengyi.bean;

public class ProductMatchBean {
    private String productMaterialMatch;
    private String productMatch;
    private String productLine;
    private String productMaterialYarn;
    private String company;
    private String productSpecificationsMatch;
    private String productSpecificationsYarn;

    public String getProductMaterialYarn() {
        return productMaterialYarn;
    }

    public void setProductMaterialYarn(String productMaterialYarn) {
        this.productMaterialYarn = productMaterialYarn;
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
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public ProductMatchBean(String productMaterialMatch, String productMatch, String productLine, String productMaterialYarn, String productSpecificationsMatch, String productSpecificationsYarn,String company) {

        this.productMaterialMatch = productMaterialMatch;
        this.productMatch = productMatch;
        this.productLine = productLine;
        this.productMaterialYarn = productMaterialYarn;
        this.company=company;
        this.productSpecificationsMatch = productSpecificationsMatch;
        this.productSpecificationsYarn = productSpecificationsYarn;
    }

    public String getProductMaterialMatch() {
        return productMaterialMatch;
    }

    public void setProductMaterialMatch(String productMaterialMatch) {
        this.productMaterialMatch = productMaterialMatch;
    }

    public String getProductMatch() {
        return productMatch;
    }

    public void setProductMatch(String productMatch) {
        this.productMatch = productMatch;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public ProductMatchBean(String productMaterialMatch, String productMatch, String productLine) {
        this.productMaterialMatch = productMaterialMatch;
        this.productMatch = productMatch;
        this.productLine = productLine;
    }
    public ProductMatchBean(){}
}
