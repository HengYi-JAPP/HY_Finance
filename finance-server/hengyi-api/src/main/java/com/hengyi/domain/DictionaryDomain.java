package com.hengyi.domain;

/**
 * @author liuyuan
 * @create 2018-03-24 18:02
 * @description 字典实体类，与数据库字典表相对应
 **/
public class DictionaryDomain {
    //字典表主键
    Integer id;
    //字典表字段类型
    String dictType;
    //字典的key
    String dictKey;
    //字典的value；
    String dictValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }
}
