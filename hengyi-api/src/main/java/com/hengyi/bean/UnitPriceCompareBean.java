package com.hengyi.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

public class UnitPriceCompareBean {

    private BigDecimal id;
    private String company;

    private BigDecimal month;
    private BigDecimal year;
    private String product;

    private String workshop;

    private String line;

    private String spec;

    private String yarnkind;

    private BigDecimal totalProduct;
    private BigDecimal budgetTotalProduct;


    private BigDecimal productUnitPrice;
    private BigDecimal checkProductUnitPrice;
    private BigDecimal checkBudgetUnitPrice;
    private BigDecimal BudgetUnitPrice;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

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

    public String getYarnkind() {
        return yarnkind;
    }

    public void setYarnkind(String yarnkind) {
        this.yarnkind = yarnkind;
    }

    public BigDecimal getBudgetTotalProduct() {
        return budgetTotalProduct;
    }

    public void setBudgetTotalProduct(BigDecimal budgetTotalProduct) {
        this.budgetTotalProduct = budgetTotalProduct;
        this.budgetTotalProduct.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(BigDecimal productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
        this.productUnitPrice.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getCheckProductUnitPrice() {
        return checkProductUnitPrice;
    }

    public void setCheckProductUnitPrice(BigDecimal checkProductUnitPrice) {
        this.checkProductUnitPrice = checkProductUnitPrice;
        this.checkProductUnitPrice.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getCheckBudgetUnitPrice() {
        return checkBudgetUnitPrice;
    }

    public void setCheckBudgetUnitPrice(BigDecimal checkBudgetUnitPrice) {
        this.checkBudgetUnitPrice = checkBudgetUnitPrice;
        this.checkBudgetUnitPrice.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getBudgetUnitPrice() {
        return BudgetUnitPrice;
    }

    public void setBudgetUnitPrice(BigDecimal budgetUnitPrice) {
        BudgetUnitPrice = budgetUnitPrice;
        BudgetUnitPrice.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(BigDecimal totalProduct) {
        this.totalProduct = totalProduct;
        this.totalProduct.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }
}