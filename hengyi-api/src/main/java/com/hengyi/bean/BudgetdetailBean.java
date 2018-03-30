package com.hengyi.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BudgetdetailBean {

    private BigDecimal id;
    private String company;

    private BigDecimal month;
    private BigDecimal year;


    private String product;

    private String workshop;

    private String line;

    private String spec;

    private String yarnkind;

    private BigDecimal aarate;

    private BigDecimal fsrate;

    private BigDecimal dayProduct;

    private BigDecimal budgetTotalProduct;


    private String type;

    private ArrayList<MaterialcostdetailsBean> materialcostdetailsBeanArrayList=new ArrayList<MaterialcostdetailsBean>();

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

    public BigDecimal getAarate() {
        return aarate;
    }

    public void setAarate(BigDecimal aarate) {
        this.aarate = aarate;
    }

    public BigDecimal getFsrate() {
        return fsrate;
    }

    public void setFsrate(BigDecimal fsrate) {
        this.fsrate = fsrate;
    }

    public BigDecimal getDayProduct() {
        return dayProduct;
    }

    public void setDayProduct(BigDecimal dayProduct) {
        this.dayProduct = dayProduct;
    }

    public BigDecimal getBudgetTotalProduct() {
        return budgetTotalProduct;
    }

    public void setBudgetTotalProduct(BigDecimal budgetTotalProduct) {
        this.budgetTotalProduct = budgetTotalProduct;
        this.budgetTotalProduct.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<MaterialcostdetailsBean> getMaterialcostdetailsBeanArrayList() {
        return materialcostdetailsBeanArrayList;
    }

    public void setMaterialcostdetailsBeanArrayList(ArrayList<MaterialcostdetailsBean> materialcostdetailsBeanArrayList) {
        this.materialcostdetailsBeanArrayList = materialcostdetailsBeanArrayList;
    }
    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public BudgetdetailBean() {
    }



    @Override
    public String toString() {
        return "BudgetdetailBean{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", product='" + product + '\'' +
                ", workshop='" + workshop + '\'' +
                ", line='" + line + '\'' +
                ", spec='" + spec + '\'' +
                ", yarnkind='" + yarnkind + '\'' +
                ", aarate=" + aarate +
                ", fsrate=" + fsrate +
                ", dayProduct=" + dayProduct +
                ", budgetTotalProduct=" + budgetTotalProduct +
                ", type='" + type + '\'' +
                ", materialcostdetailsBeanArrayList=" + materialcostdetailsBeanArrayList +
                '}';
    }
}