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
     * 按公司获得18年以后的数据
     *
     * @param id
     * @return
     */
    ArrayList<SapDataBean> selectsapdatabycompany(SapDataMonthBean sapDataMonthBean);

}