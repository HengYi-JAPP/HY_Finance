package com.hengyi.job;


/**
 * @author: liuyuan
 * @Description:客户配置
 * @Date: 2017/10/31 10:11
 */
public interface FinanceDataTask {
    /***
     *
     * @param
     * @return
     * 生产线-规格-差异化维度     详情数据计算定时任务
     */
    public void productlinedatatask();

    /***
     *
     * @param
     * @return
     * 生产线-规格-差异化维度     单位成本计算定时任务
     */
    public void unitpricecomparetask();

    /****
     * 详情数据定时任务（用于获取新增规格）
     */
    public void productlinedatatask2();
}
