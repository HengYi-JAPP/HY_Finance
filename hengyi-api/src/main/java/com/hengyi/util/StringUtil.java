package com.hengyi.util;

public class StringUtil {
    public static boolean equals(String str1, String str2){
        return  (str1+"").equals((str2+""));
    }
    public static Boolean isNotEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        return true;
    }

}
