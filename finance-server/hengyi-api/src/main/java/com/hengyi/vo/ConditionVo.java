package com.hengyi.vo;

import com.hengyi.util.QueryDtoBase;

/**
 * @author liuyuan
 * @create 2018-03-22 14:39
 * @description 传入的参数查询条件实体类
 **/
public class ConditionVo extends QueryDtoBase{
    //年
    Integer year;
    //月
    Integer month;
    //公司
    String company;
    //产品
    String product;
    //车间
    String workshop;
    //生产线
    String productLine;
    //规格
    String spec;
    //成本类型
    String priceOrconsumer;
    //类型：实际或者预算
    String type;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

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

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPriceOrconsumer() {
        return priceOrconsumer;
    }

    public void setPriceOrconsumer(String priceOrconsumer) {
        this.priceOrconsumer = priceOrconsumer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
