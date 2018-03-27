package com.hengyi.util;

/**
 * 返回编码
 * */
public enum ResponseCode {
    SUCCESS(1, "SUCCESS"),
    ERROR(0, "ERROR");

    private final int code;
    private final String desc;


    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}