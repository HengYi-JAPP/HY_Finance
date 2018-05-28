package com.hengyi.mapper;

import com.hengyi.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: QZM
 * @Description:财务预算分析Mysql持久层接口
 * @Date: 2017/12/13 14:04
 */
@Repository
public interface FinanceDataMapper {

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
     * 查询特殊物料单价表 （电费、燃料等）
     *
     * @param
     * @return
     */
    public ArrayList<MaterialPriceBean> selectpricelist ();

    /**
     * 根据key查询业务字典
     *
     * @param
     * @return
     */
    public DictBean selectsysdictbykey(String key);

    /**
     * 按月份清空FinanceSapData表，以便数据重新导入
     *
     * @param
     * @return
     */
   public void deleteFinanceSapDatabymonth(SapDataMonthBean sapDataDelByMonthBean);

    /**
     * 将解析后的SAP数据插入本系统Mysql数据库FinanceSapData中
     *
     * @param financeSapDataInsertBean
     * @return
     */
    int insertsapdata(FinanceSapDataInsertBean financeSapDataInsertBean);

    /**
     * 按月份清空Budgetdetail表，以便数据重复写入
     *
     * @param
     * @return
     */
   public void deletebudgetbymonth(SapDataMonthBean sapDataDelByMonthBean);

    /***
     * 按月份清空materialcostDetails表中的数据
     * @param sapDataMonthBean
     */
   public void deleteMaterialCostDetails(SapDataMonthBean sapDataMonthBean);

    /**
     * 查找FinanceSapData表中的生产线-规格对应成本要素数据
     *
     * @param
     * @return
     */
   public ArrayList<MaterialOfLineSelectBean> selectmaterialofline(SapDataMonthBean onthBean);

    /**
     * 插入数据到物料消耗详情表中
     *
     * @param
     * @return
     */
   public int insertmaterialcostdetails( MaterialcostdetailsBean materialcostdetailsBean);

    /**
     * 通过物料匹配关系表的materialName 来寻找详情表的字段名
     *
     * @param
     * @return
     */
   public ArrayList<String> selectfieldbymaterialname(String materialName);

    /**
     * 插入一条详情数据
     *
     * @param
     * @return
     */
   public int insertbudgetdetail(@Param("bdb") BudgetdetailBean budgetdetailBean);

    /**
     * 查找所有详情表中的数据并返回一个 ArrayList<Map<String,Object>>
     *
     * @param
     * @return
     */
   public ArrayList<Map<String,Object>> selectbudgetdata();

    /**
     * 查找上个月详情表中的数据并返回一个 ArrayList<Map<String,Object>>
     *
     * @param sapDataMonthBean
     * @return
     */
   public ArrayList<Map<String,Object>> selectbudgetdatabymonth(SapDataMonthBean sapDataMonthBean);

    /**
     * 查找预算的详情数据
     *
     * @param
     * @return
     */
   public ArrayList<LinkedHashMap<String,Object>> selectproductbudgetdata();

    /**
     * 根据年、月查询详情表数据
     *
     * @param sapDataMonthBean
     * @return
     */
   public ArrayList<Map<String,Object>> selectbudgetdatabydate(SapDataMonthBean sapDataMonthBean);

    /**
     * 根据ID查询该成本项的单价、单耗、单位成本等信息
     *
     * @param id
     * @return
     */
   public MaterialcostdetailsBean selectcostdetailbyid(Integer id);

    /**
     * 根据ID集合来查询成本项的单价、单耗、单位成本等信息
     *
     * @param list
     * @return
     */
    public ArrayList<MaterialcostdetailsBean> selectcostdetailbyidlist(ArrayList<Integer> list);

    /**
     * 插入一条预算的详情数据到BudgetDetail表中
     *
     * @param
     * @return
     */
   public int insertdetail(@Param("bdb") BudgetdetailBean budgetdetailBean);

    /**
     * 插入一条生产线、规格、纱种（差异化）维度的 单位成本、修正数据
     *
     * @param unitPriceCompareBean
     * @return
     */
   public int insertunitpricecomparedata(UnitPriceCompareBean unitPriceCompareBean);

    /**
     * 根据公司、年、月、生产线、规格、纱种（差异化）来查询该维度的实际总产量
     *
     * @param materialOfLineSelectBean
     * @return
     */
   public Double selectproductquantity(MaterialOfLineSelectBean materialOfLineSelectBean);
    /**
     * 删除上月UnitPriceCompare表中数据
     *
     * @param sapDataMonthBean
     * @return
     */
    public void deleteunitpricecomparebymonth(SapDataMonthBean sapDataMonthBean);
    /**
     * 插入预算数据时，先搜索判断该数据是否已插入
     *
     * @param
     * @return
     */
    public  ArrayList<String> selectbudgetdatabybean(BudgetdetailBean budgetdetailBean);

    /***
     * 更新考核维度考核维度预算单位成本
     * @param
     */
    public void updateCheckProductUnitPrice(MaterialcostdetailsBean materialcostdetailsBean);

    /***
     * 同步SAP数据前需要将当月的数据进行一个删除，以避免重复数据
     * @param sapDataDelByMonthBean
     */
    public void deleteFinanceSapDatabymonth2(SapDataMonthBean sapDataDelByMonthBean);

    /***
     * 插入新新增表之前需要删除当月
     * @param sapDataDelByMonthBean
     */
    public void deletebudgetbymonth2(SapDataMonthBean sapDataDelByMonthBean);

    /***
     * 插入数据之前需要对新增表进行一个查询，以防止重复数据出现
     * @param budgetdetailBean
     * @return
     */
//    public ArrayList<String> selectBudgetdetailAdd(BudgetdetailBean budgetdetailBean);

    /***
     * 将解析同步过来的数据放到FinanceSapDataAdd表中
     * @param financeSapDataInsertBean
     * @return
     */
    int insertsapdata2(FinanceSapDataInsertBean financeSapDataInsertBean);

    /***
     * 实际数据插入预算详情数据（budgetdetailAdd）
     * @param budgetdetailBean
     * @return
     */
    public int insertbudgetdetail2(@Param("bdb") BudgetdetailBean budgetdetailBean);

    /***
     * 预算数据插入详情（budgetdetailAdd）
     * @param budgetdetailBean
     * @return
     */
    public int insertdetail2(@Param("bdb") BudgetdetailBean budgetdetailBean);

    /***
     * 查询出bugetdetailAdd中的实际数据
     * @param budgetdetailBean
     * @return
     */
    public  ArrayList<String> selectbudgetdatabybean2(BudgetdetailBean budgetdetailBean);

    /***
     * 获取SAP同步过来的数据（查询FinanceSapDataAdd）
     * @param onthBean
     * @return
     */
    public ArrayList<MaterialOfLineSelectBean> selectmaterialofline2(SapDataMonthBean onthBean);

    /***
     * 来查询该维度的总产量
     * @param materialOfLineSelectBean
     * @return
     */
    public Double selectproductquantity2(MaterialOfLineSelectBean materialOfLineSelectBean);

    /***
     * 插入未匹配到的物料Id
     * @param materialOfLineSelectBean
     */
    public void insertUnmatchedMaterial(MaterialOfLineSelectBean materialOfLineSelectBean);

}