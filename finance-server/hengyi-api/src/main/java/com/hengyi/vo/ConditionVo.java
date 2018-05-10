package com.hengyi.vo;

import com.hengyi.util.QueryDtoBase;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author liuyuan
 * @create 2018-03-22 14:39
 * @description 传入的参数查询条件实体类
 **/
public class ConditionVo extends QueryDtoBase{
    // 开始月份(含月份)
    String startMonth;
    //结束月份
    String endMonth;
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
    // 是否分阶段标志
    String stageType;
    // 是否考核维度的标识
    String dimension;

//    public Integer getYear() {
//        return year;
//    }
//
//    public void setYear(Integer year) {
//        this.year = year;
//    }
//
//    public Integer getMonth() {
//        return month;
//    }
//
//    public void setMonth(Integer month) {
//        this.month = month;
//    }


    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
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

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public ConditionVo(HttpServletRequest request) {
        try {
            //设置编码类型，解决中文乱码问题
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.startMonth = request.getParameter("startMonth");
        this.endMonth = request.getParameter("endMonth");
        this.company = request.getParameter("company");
        this.product = request.getParameter("product");
        this.workshop = request.getParameter("workshop");
        this.productLine = request.getParameter("productLine");
        this.spec = request.getParameter("spec");
        this.priceOrconsumer = request.getParameter("priceOrconsumer");
        this.type = request.getParameter("type");
        this.stageType = request.getParameter("stageType");
        this.dimension = request.getParameter("dimension");
    }

    public ConditionVo() {}
}
