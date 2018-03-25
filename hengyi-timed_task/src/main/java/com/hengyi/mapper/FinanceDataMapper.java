package com.hengyi.mapper;

import com.hengyi.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author: QZM
 * @Description:财务预算分析Mysql持久层接口
 * @Date: 2017/12/13 14:04
 */
@Repository
public interface FinanceDataMapper {
    /**
     * 将数据插入本系统Mysql数据库中
     *
     * @param financeSapDataInsertBean
     * @return
     */
     int insertsapdata(FinanceSapDataInsertBean financeSapDataInsertBean);

    /**
     * 查找所有公司编号
     *
     * @param
     * @return
     */
    public  ArrayList<String> selectallcompany();
    /**
     * 查找生产线匹配关系
     *
     * @param
     * @return
     */
   public ArrayList<ProductMatchBean>  selectproductmatch();
    /**
     * 查找物料匹配关系
     *
     * @param
     * @return
     */
    public ArrayList<MaterialMatchBean>  selectmaterialmatch();
    /**
     * 清空FinanceSapData表，以便数据重新导入
     *
     * @param
     * @return
     */
   public void deleteFinanceSapDatabymonth(SapDataMonthBean sapDataDelByMonthBean);
    /**
     * 按月份清空Budgetdetail表，以便数据重复
     *
     * @param
     * @return
     */
   public void deletebudgetbymonth(SapDataMonthBean sapDataDelByMonthBean);
    /**
     * 查找生产线-规格对应成本要素数据
     *
     * @param
     * @return
     */
   public ArrayList<MaterialOfLineSelectBean> selectmaterialofline(SapDataMonthBean onthBean);
   public int insertmaterialcostdetails( MaterialcostdetailsBean materialcostdetailsBean);
   public String selectfieldbymaterialname(String materialName);
   public int insertbudgetdetail(@Param("bdb") BudgetdetailBean budgetdetailBean);
   public ArrayList<Map<String,Object>> selectbudgetdata();
   public ArrayList<Map<String,Object>> selectbudgetdatabydate(SapDataMonthBean sapDataMonthBean);
   public MaterialcostdetailsBean selectcostdetailbyid(Integer id);
   public DictBean selectsysdictbykey(String key);

}