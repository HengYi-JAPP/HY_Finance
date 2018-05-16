package com.hengyi.bean;

/**
 * @author liuyuan
 * @create 2018-05-14 23:06
 * @description 该实体类用于封装实际产量查询返回结果
 **/
public class FactProductBean {
//    private String id;
    //公司
    private String company;
    //产品
    private String product;
    //实际产量
    private String factProduct;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

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

    public String getFactProduct() {
        return factProduct;
    }

    public void setFactProduct(String factProduct) {
        this.factProduct = factProduct;
    }
}
