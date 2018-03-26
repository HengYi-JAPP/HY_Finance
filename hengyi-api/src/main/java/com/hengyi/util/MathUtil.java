package com.hengyi.util;

import java.math.BigDecimal;

public  class MathUtil {
    //保留二位小数除法，b1被除数 b2除数
    public static BigDecimal divide(BigDecimal b1,BigDecimal b2){
    if (b1==null||b1.intValue()==0||b2==null||b2.intValue()==0){
    return new BigDecimal(0);
    }
        return  b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);

    }
    public static BigDecimal add(BigDecimal b1,BigDecimal b2){
        return b1.add(b2);
    }
    public static boolean bigdecimalequals(BigDecimal b1,BigDecimal b2){
        if (b1==null&&b2==null){
            return true;
        }
        if (b1!=null&&b2==null){
            return false;
        }
        if (b1==null&&b2!=null){
            return false;
        }
        return  b1.equals(b2);

    }

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!(Character.isDigit(str.charAt(i))|| str.charAt(i)=='.')){
                return false;
            }
        }
        return true;
    }


}
