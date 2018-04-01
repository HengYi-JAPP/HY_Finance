package com.hengyi.bean;

import java.math.BigDecimal;

/**
 * @author: QZM
 * @Description:SAP数据Bean
 * @Date: 2018/3/15 8:00
 */
public class SapDataBean {
    //订单号id
    private String orderId;
    //SAP成本要素id
    private String costId;
    //月份
    private BigDecimal month;
    //年份
    private BigDecimal year;
    //SAP成本物料号
    private String costMaterialId;
    //SAP成本物料描述
    private String costMaterialDescribe;
    //SAP成本工艺阶段
    private String state;
    //SAP订单类型
    private String leibie;
    //SAP成本要素描述
    private String costDescribe;
    //SAP产品物料号
    private String sapMaterialId;
    //产品生产公司
    private String company;
    //SAP产品物料描述
    private String sapMaterialDescribe;
    //SAP订单产品数量
    private BigDecimal orderProductQuantity;
    //SAP成本要素金额
    private BigDecimal money;
    //SAP成本要素数量
    private BigDecimal costQuantity;
    //车间
    private String workShop;



    @Override
    public String toString() {
        return "SapDataBean{" +
                "orderId='" + orderId + '\'' +
                ", costId='" + costId + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", costMaterialId='" + costMaterialId + '\'' +
                ", costMaterialDescribe='" + costMaterialDescribe + '\'' +
                ", state='" + state + '\'' +
                ", leibie='" + leibie + '\'' +
                ", costDescribe='" + costDescribe + '\'' +
                ", sapMaterialId='" + sapMaterialId + '\'' +
                ", company='" + company + '\'' +
                ", sapMaterialDescribe='" + sapMaterialDescribe + '\'' +
                ", orderProductQuantity=" + orderProductQuantity +
                ", money=" + money +
                ", costQuantity=" + costQuantity +
                '}';
    }
    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCostId() {
        return costId;
    }

    public void setCostId(String costId) {
        this.costId = costId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLeibie() {
        return leibie;
    }

    public void setLeibie(String leibie) {
        this.leibie = leibie;
    }

    public String getCostDescribe() {
        return costDescribe;
    }

    public void setCostDescribe(String costDescribe) {
        this.costDescribe = costDescribe;
    }

    public String getSapMaterialId() {
        return sapMaterialId;
    }

    public void setSapMaterialId(String sapMaterialId) {
        this.sapMaterialId = sapMaterialId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSapMaterialDescribe() {
        return sapMaterialDescribe;
    }

    public void setSapMaterialDescribe(String sapMaterialDescribe) {
        this.sapMaterialDescribe = sapMaterialDescribe;
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

    public SapDataBean() {
    }

    public SapDataBean(String orderId, String costId, BigDecimal month, BigDecimal year, String costMaterialId, String costMaterialDescribe, String state, String leibie, String costDescribe, String sapMaterialId, String company, String sapMaterialDescribe, BigDecimal orderProductQuantity, BigDecimal money, BigDecimal costQuantity, String workShop) {
        this.orderId = orderId;
        this.costId = costId;
        this.month = month;
        this.year = year;
        this.costMaterialId = costMaterialId;
        this.costMaterialDescribe = costMaterialDescribe;
        this.state = state;
        this.leibie = leibie;
        this.costDescribe = costDescribe;
        this.sapMaterialId = sapMaterialId;
        this.company = company;
        this.sapMaterialDescribe = sapMaterialDescribe;
        this.orderProductQuantity = orderProductQuantity;
        this.orderProductQuantity.setScale(5,   BigDecimal.ROUND_HALF_UP);
        this.money = money;
        this.money.setScale(5,   BigDecimal.ROUND_HALF_UP);
        this.costQuantity = costQuantity;
        this.costQuantity.setScale(5,   BigDecimal.ROUND_HALF_UP);
        this.workShop = workShop;
    }
}
