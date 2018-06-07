package com.hengyi.util;

import com.hengyi.bean.SapDataMonthBean;

import java.math.BigDecimal;
import java.util.Date;

public class DateUtil {
    public static SapDataMonthBean getsapdatamonthbeannow(){
        Date date = new Date();
        //获取当前年份
        BigDecimal year=new BigDecimal(String.format("%tY",date));
        //获取当前月份
        BigDecimal month=new BigDecimal(String.format("%tm",date));
        //获取当前日子
        BigDecimal day=new BigDecimal(String.format("%td",date));
        if (day.intValue()<15){//如果是月初的同步需要取上个月的数据
            if (month.intValue()==1){
                month= new BigDecimal("12");
                year=year.subtract(new BigDecimal("1"));
            }else {
                month = month.subtract(new BigDecimal("1"));
            }
        }
        return  new SapDataMonthBean( month,year,"" );
    }
}
