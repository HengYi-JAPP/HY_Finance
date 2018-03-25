package com.hengyi.bean;

import com.hengyi.util.MathUtil;

import java.math.BigDecimal;

/**
 * @author: QZM
 * @Description:SAP数据Bean
 * @Date: 2018/3/15 8:00
 */
public class MaterialOfLineSelectBean {
    //产品生产公司
    private String company;
    //年份
    private BigDecimal year;
    //月份
    private BigDecimal month;
    //SAP成本要素对应产品名称
    private String productName;
    //SAP成本要素对应产品规格
    private String productSpecifications;
    //SAP成本要素对应产品等级
    private String productLine;
    //SAP成本工艺阶段
    private String state;
    //车间
    private String workShop;
    //SAP成本要素id
    private String costId;
    //SAP成本要素描述
    private String costDescribe;
    //SAP成本物料号
    private String costMaterialId;
    //SAP成本物料描述
    private String costMaterialDescribe;
    //SAP订单产品数量
    private BigDecimal orderProductQuantity;
    //SAP成本要素总价
    private BigDecimal money;
    //SAP成本要素总耗
    private BigDecimal costQuantity;
    //SAP成本要素对应产品等级
    private String productGrade;

    private String productYarn;

    public MaterialOfLineSelectBean() {
    }

    public MaterialOfLineSelectBean(String company, BigDecimal year, BigDecimal month, String productName, String productSpecifications, String productLine, String state, String workShop, String costId, String costDescribe, String costMaterialId, String costMaterialDescribe, BigDecimal orderProductQuantity, BigDecimal money, BigDecimal costQuantity, String productGrade, String productYarn) {
        this.company = company;
        this.year = year;
        this.month = month;
        this.productName = productName;
        this.productSpecifications = productSpecifications;
        this.productLine = productLine;
        this.state = state;
        this.workShop = workShop;
        this.costId = costId;
        this.costDescribe = costDescribe;
        this.costMaterialId = costMaterialId;
        this.costMaterialDescribe = costMaterialDescribe;
        this.orderProductQuantity = orderProductQuantity;
        this.money = money;
        this.costQuantity = costQuantity;
        this.productGrade = productGrade;
        this.productYarn = productYarn;
    }

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpecifications() {
        return productSpecifications;
    }

    public void setProductSpecifications(String productSpecifications) {
        this.productSpecifications = productSpecifications;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
    }

    public String getCostDescribe() {
        return costDescribe;
    }

    public void setCostDescribe(String costDescribe) {
        this.costDescribe = costDescribe;
    }

    public String getCostMaterialId() {
        return costMaterialId;
    }

    public void setCostMaterialId(String costMaterialId) {
        this.costMaterialId = costMaterialId;
    }

    public String getCostMaterialDescribe() {
        return costMaterialDescribe;
    }

    public void setCostMaterialDescribe(String costMaterialDescribe) {
        this.costMaterialDescribe = costMaterialDescribe;
    }

    public BigDecimal getOrderProductQuantity() {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(BigDecimal orderProductQuantity) {
        this.orderProductQuantity = orderProductQuantity;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getCostQuantity() {
        return costQuantity;
    }

    public void setCostQuantity(BigDecimal costQuantity) {
        this.costQuantity = costQuantity;
    }

    public String getProductGrade() {
        return productGrade;
    }

    public void setProductGrade(String productGrade) {
        this.productGrade = productGrade;
    }

    public String getProductYarn() {
        return productYarn;
    }

    public void setProductYarn(String productYarn) {
        this.productYarn = productYarn;
    }
}
