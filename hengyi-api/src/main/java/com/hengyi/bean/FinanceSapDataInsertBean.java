package com.hengyi.bean;

import com.hengyi.util.MathUtil;

import java.math.BigDecimal;

/**
 * @author: QZM
 * @Description:SAP数据Bean
 * @Date: 2018/3/15 8:00
 */
public class FinanceSapDataInsertBean {

    //订单号id
    private String orderId;
    //SAP成本要素id
    private String costId;
    //月份
    private BigDecimal month;
    //年份
    private BigDecimal year;
    //车间
    private String workShop ;
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
    //SAP成本要素总价
    private BigDecimal money;
    //SAP成本要素总耗
    private BigDecimal costQuantity;
    //SAP成本要素单价（元/吨）
    private BigDecimal unitPrice;
    //SAP成本要素单耗（千克/吨）
    private BigDecimal consumption;
    //SAP成本要素对应产品名称
    private String productName;
    //SAP成本要素对应产品规格
    private String productSpecifications;
    //SAP成本要素对应产品批号
    private String productBatchNumber;
    //SAP成本要素对应产品等级
    private String productGrade;
    //SAP成本要素对应产品等级
    private String productLine;
    //纱种
    private String productYarn;

    public FinanceSapDataInsertBean(SapDataBean sapDataBean,String productName,String productSpecifications ,String productBatchNumber,String productGrade,String productLine,String productYarn){
        this.company=sapDataBean.getCompany();
        this.workShop=sapDataBean.getWorkShop();
        this.orderId=sapDataBean.getOrderId();
        this.year=sapDataBean.getYear();
        this.state=sapDataBean.getState();
        this.orderProductQuantity=sapDataBean.getOrderProductQuantity();
        this.sapMaterialDescribe=sapDataBean.getSapMaterialDescribe();
        this.costId=sapDataBean.getCostId();
        this.costDescribe=sapDataBean.getCostDescribe();
        this.costMaterialDescribe=sapDataBean.getCostMaterialDescribe();
        this.costMaterialId=sapDataBean.getCostMaterialId();
        this.leibie=sapDataBean.getLeibie();
        this.money=sapDataBean.getMoney();
        this.month=sapDataBean.getMonth();
        this.costQuantity=sapDataBean.getCostQuantity();
        this.sapMaterialId=sapDataBean.getSapMaterialId();
        this.unitPrice= MathUtil.divide(money,MathUtil.divide(orderProductQuantity,new BigDecimal("1000")));
        this.consumption=MathUtil.divide(costQuantity,MathUtil.divide(orderProductQuantity,new BigDecimal("1000")));
        this.productName=productName;
        this.productSpecifications=productSpecifications;
        this.productBatchNumber=productBatchNumber;
        this.productGrade=productGrade;
        this.productLine=productLine;
        this.productYarn= productYarn;
    }

    public String getWorkShop() {
        return workShop;
    }

    public void setWorkShop(String workShop) {
        this.workShop = workShop;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
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

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public String getProductYarn() {
        return productYarn;
    }

    public void setProductYarn(String productYarn) {
        this.productYarn = productYarn;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
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

    public String getProductBatchNumber() {
        return productBatchNumber;
    }

    public void setProductBatchNumber(String productBatchNumber) {
        this.productBatchNumber = productBatchNumber;
    }

    public String getProductGrade() {
        return productGrade;
    }

    public void setProductGrade(String productGrade) {
        this.productGrade = productGrade;
    }

    public FinanceSapDataInsertBean(String orderId, String costId, BigDecimal month, BigDecimal year, String workShop, String costMaterialId, String costMaterialDescribe, String state, String leibie, String costDescribe, String sapMaterialId, String company, String sapMaterialDescribe, BigDecimal orderProductQuantity, BigDecimal money, BigDecimal costQuantity, BigDecimal unitPrice, BigDecimal consumption, String productName, String productSpecifications, String productBatchNumber, String productGrade, String productLine, String productYarn) {
        this.orderId = orderId;
        this.costId = costId;
        this.month = month;
        this.year = year;
        this.workShop = workShop;
        this.costMaterialId = costMaterialId;
        this.costMaterialDescribe = costMaterialDescribe;
        this.state = state;
        this.leibie = leibie;
        this.costDescribe = costDescribe;
        this.sapMaterialId = sapMaterialId;
        this.company = company;
        this.sapMaterialDescribe = sapMaterialDescribe;
        this.orderProductQuantity = orderProductQuantity;
        this.money = money;
        this.costQuantity = costQuantity;
        this.unitPrice = unitPrice;
        this.consumption = consumption;
        this.productName = productName;
        this.productSpecifications = productSpecifications;
        this.productBatchNumber = productBatchNumber;
        this.productGrade = productGrade;
        this.productLine = productLine;
        this.productYarn = productYarn;
    }
}
