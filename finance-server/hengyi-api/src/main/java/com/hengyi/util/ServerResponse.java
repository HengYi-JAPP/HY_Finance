package com.hengyi.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hengyi.util.Page;
import java.io.Serializable;

/**
 * 返回实体类对象
 * */
public class ServerResponse<T> implements Serializable {
    private Integer messageType;
    private String message;
    private T data;
    private Page page;

    /**辅助变量, mock对象转换需要data class, 否则无法反射注入*/
    private Class<T> dataClazz;
    /**辅助变量, mock对象转换判断是否List, 否则无法反射注入*/
    private Boolean isDataSingle;

    public ServerResponse(){}

    public ServerResponse(Integer messageType) {
        this.messageType = messageType;
    }
    public ServerResponse(Integer messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }
    public ServerResponse(Integer messageType, T data) {
        this.messageType = messageType;
        this.data = data;
    }

    public ServerResponse(Integer messageType, String message, T data) {
        this.messageType = messageType;
        this.message = message;
        this.data = data;
    }

    public ServerResponse(Integer messageType, String message, T data, Page page) {
        this.messageType = messageType;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public Page getPage() {
        return page;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static <T> ServerResponse<T> createBySuccess(String message, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ServerResponse<T> createBySuccess(String message, T data, Page page){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), message, data, page);
    }

    public static <T> ServerResponse<T> createByError(String message){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), message);
    }

    @JsonIgnore
    public Class<T> getDataClazz() {
        return dataClazz;
    }

    public void setDataClazz(Class<T> dataClazz) {
        this.dataClazz = dataClazz;
    }

    @JsonIgnore
    public Boolean getDataSingle() {
        return isDataSingle;
    }

    public void setDataSingle(Boolean dataSingle) {
        isDataSingle = dataSingle;
    }
}
