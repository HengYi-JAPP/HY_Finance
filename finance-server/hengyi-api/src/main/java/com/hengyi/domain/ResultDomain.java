package com.hengyi.domain;

import java.math.BigDecimal;

/**
 * @author liuyuan
 * @create 2018-03-27 9:40
 * @description
 **/
public class ResultDomain
{
    // 主键id
//    Integer id;
    // 年
    Integer year;
    // 月
    Integer month;
    // 公司
    String company;
    // 产品
    String product;
    // 车间
//    String workshop;
    // 生产线
    String line;
    // 规格
    String spec;
    // 纱种
    String yarnKind;
    //预算产量
    BigDecimal budgetTotalProduct;
    // 实际产量
    BigDecimal totalProduct;
    // 实际单位成本
    BigDecimal productUnitPrice;
    //考核维度实际单位成本
    BigDecimal checkProductUnitPrice;
    //考核维度预算单位成本
    BigDecimal checkBudgetUnitPrice;
    // 目标预算单位成本
    BigDecimal budgetUnitPrice;
    // 单价变动影响单位成本
    BigDecimal priceEffect;
    // 单耗变动影响单位成本
    BigDecimal consumerEffect;
    // 结构变动影响单位成本
    BigDecimal structureEffect;
    // 总体差异
    BigDecimal totalDifference;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

//    public String getWorkshop() {
//        return workshop;
//    }
//
//    public void setWorkshop(String workshop) {
//        this.workshop = workshop;
//    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getYarnKind() {
        return yarnKind;
    }

    public void setYarnKind(String yarnKind) {
        this.yarnKind = yarnKind;
    }

    public BigDecimal getBudgetTotalProduct() {
        return budgetTotalProduct;
    }

    public void setBudgetTotalProduct(BigDecimal budgetTotalProduct) {
        this.budgetTotalProduct = budgetTotalProduct;
    }

    public BigDecimal getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(BigDecimal totalProduct) {
        this.totalProduct = totalProduct;
    }

    public BigDecimal getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(BigDecimal productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public BigDecimal getCheckProductUnitPrice() {
        return checkProductUnitPrice;
    }

    public void setCheckProductUnitPrice(BigDecimal checkProductUnitPrice) {
        this.checkProductUnitPrice = checkProductUnitPrice;
    }

    public BigDecimal getCheckBudgetUnitPrice() {
        return checkBudgetUnitPrice;
    }

    public void setCheckBudgetUnitPrice(BigDecimal checkBudgetUnitPrice) {
        this.checkBudgetUnitPrice = checkBudgetUnitPrice;
    }

    public BigDecimal getBudgetUnitPrice() {
        return budgetUnitPrice;
    }

    public void setBudgetUnitPrice(BigDecimal budgetUnitPrice) {
        this.budgetUnitPrice = budgetUnitPrice;
    }

    public BigDecimal getPriceEffect() {
        return priceEffect;
    }

    public void setPriceEffect(BigDecimal priceEffect) {
        this.priceEffect = priceEffect;
    }

    public BigDecimal getConsumerEffect() {
        return consumerEffect;
    }

    public void setConsumerEffect(BigDecimal consumerEffect) {
        this.consumerEffect = consumerEffect;
    }

    public BigDecimal getStructureEffect() {
        return structureEffect;
    }

    public void setStructureEffect(BigDecimal structureEffect) {
        this.structureEffect = structureEffect;
    }

    public BigDecimal getTotalDifference() {
        return totalDifference;
    }

    public void setTotalDifference(BigDecimal totalDifference) {
        this.totalDifference = totalDifference;
    }
}
