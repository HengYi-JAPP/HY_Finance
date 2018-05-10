package com.hengyi.domain;

/**
 * @author liuyuan
 * @create 2018-05-02 20:44
 * @description 新增规格实体类，用于查询出所有新增规格
 **/
public class DetailAddDomain {
    //年
    Integer year;
    //月
    Integer month;
    //公司
    String company;
    //产品
    String product;
    //车间
    String workShop;
    //生产线
    String line;
    //规格
    String spec;
    //纱种
    String yarnKind;

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

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
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

    public String getYarnKind() {
        return yarnKind;
    }

    public void setYarnKind(String yarnKind) {
        this.yarnKind = yarnKind;
    }
}
