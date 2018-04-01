package com.hengyi.util;

import com.hengyi.bean.SapDataMonthBean;

import java.math.BigDecimal;
import java.util.Date;

public class DateUtil {
    public static SapDataMonthBean getsapdatamonthbeannow(){
        Date date = new Date();
        BigDecimal month=new BigDecimal(String.format("%tm", date));
        if (month.intValue()==1){
            month= new BigDecimal("13");
        }
        return  new SapDataMonthBean( month.subtract(new BigDecimal(1)),new BigDecimal(String.format("%tY", date)),"" );
    }
}
