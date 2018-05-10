package com.hengyi.job;


/**
 * @author: liuyuan
 * @Description:客户配置
 * @Date: 2017/10/31 10:11
 */
public interface SapDataTask {
    /***
     *
     * @param
     * @return
     * SAP数据同步定时任务
     */
    void getsapdata();

    /***
     * SAP同步数据到budgetdetailAdd表，用于获取新增数据的情况
     */
    void getsapdata2();
}
