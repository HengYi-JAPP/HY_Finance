package com.hengyi.bean;

import java.math.BigDecimal;

public class MaterialcostdetailsBean {
    private Integer id;

    private BigDecimal consumption;

    private BigDecimal unitPrice;

    private String materialName;

    private String state;

    private BigDecimal price;

    private String field;

    private BigDecimal money;

    private  BigDecimal kwmeng;
    //考核维度单位成本
    private BigDecimal checkProductUnitPrice;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getKwmeng() {
        return kwmeng;
    }

    public void setKwmeng(BigDecimal kwmeng) {
        this.kwmeng = kwmeng.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
        this.consumption.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.unitPrice.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.price.setScale(5,   BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getCheckProductUnitPrice() {
        return checkProductUnitPrice;
    }

    public void setCheckProductUnitPrice(BigDecimal checkProductUnitPrice) {
        this.checkProductUnitPrice = checkProductUnitPrice;
    }

    @Override
    public String toString() {
        return "MaterialcostdetailsBean{" +
                "id=" + id +
                ", consumption=" + consumption +
                ", unitPrice=" + unitPrice +
                ", materialName='" + materialName + '\'' +
                ", state='" + state + '\'' +
                ", price=" + price +
                ", field='" + field + '\'' +
                '}';
    }
}