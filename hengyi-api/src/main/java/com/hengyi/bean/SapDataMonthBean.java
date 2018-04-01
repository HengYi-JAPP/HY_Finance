package com.hengyi.bean;

import java.math.BigDecimal;

public class SapDataMonthBean {
    private BigDecimal month;
    private BigDecimal year;
    private String company;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public SapDataMonthBean() {
    }

    public SapDataMonthBean(BigDecimal month, BigDecimal year, String company) {
        this.month = month;
        this.year = year;
        this.company = company;
    }
}
