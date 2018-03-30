package com.hengyi.sapmapper;

import com.hengyi.bean.SapDataBean;
import com.hengyi.bean.SapDataMonthBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author: QZM
 * @Description:Sap数据持久层接口
 * @Date: 2017/12/13 14:04
 */
@Repository
public interface SapDataMapper {
    /**
     * 按公司、月份、年份 获取SAP中关于预算的数据（仅2018年以后的数据）
     *
     * @param
     * @return
     */
    ArrayList<SapDataBean> selectsapdatabycompany(SapDataMonthBean sapDataMonthBean);

}