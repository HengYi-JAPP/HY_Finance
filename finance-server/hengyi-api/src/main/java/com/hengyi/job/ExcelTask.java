package com.hengyi.job;


/**
 * @author: liuyuan
 * @Description:客户配置
 * @Date: 2017/10/31 10:11
 */
public interface ExcelTask {
    /***
     *
     * @param
     * @return
     * 导入预算EXCEL
     */
    public void importexcel() throws Exception;
    /***
     *
     * @param
     * @return
     * 导出预算EXCEL
     */
    public void exportexcel() throws Exception;

    public void importexcel2() throws Exception;
}
