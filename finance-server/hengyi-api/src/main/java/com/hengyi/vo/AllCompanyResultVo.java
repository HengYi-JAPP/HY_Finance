package com.hengyi.vo;

import java.math.BigDecimal;

/**
 * @author liuyuan
 * @create 2018-03-28 16:07
 * @description
 **/
public class AllCompanyResultVo {
    // 公司
    String company;
    // 产品
    String product;
    //预算总产量
    BigDecimal budgetTotalProduct;
    // 实际总产量
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


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
